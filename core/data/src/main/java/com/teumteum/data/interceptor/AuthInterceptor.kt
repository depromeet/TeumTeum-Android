package com.teumteum.data.interceptor

import com.teumteum.data.BuildConfig.BASE_URL
import com.teumteum.data.model.response.ResponseAuthToken
import com.teumteum.domain.TeumTeumDataStore
import javax.inject.Inject
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import timber.log.Timber

class AuthInterceptor @Inject constructor(
    private val json: Json,
    private val dataStore: TeumTeumDataStore,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest = if (dataStore.isLogin) {
            originalRequest.newAuthBuilder().build()
        } else {
            originalRequest
        }
        val response = chain.proceed(authRequest)

        when (response.code) {
            CODE_TOKEN_EXPIRED -> {
                try {
                    Timber.tag("만료된 토큰").d("accessToken: ${dataStore.userToken}, refreshToken: ${dataStore.refreshToken}")
                    val refreshTokenRequest = originalRequest.newBuilder().post("".toRequestBody())
                        .url("${BASE_URL}auth/reissues")
                        .addHeader(HEADER_AUTHORIZATION, dataStore.userToken)
                        .addHeader(HEADER_REFRESH_TOKEN, dataStore.refreshToken)
                        .build()
                    val refreshTokenResponse = chain.proceed(refreshTokenRequest)

                    if (refreshTokenResponse.isSuccessful) {
                        val responseToken = json.decodeFromString(
                            refreshTokenResponse.body?.string().toString()
                        ) as ResponseAuthToken

                        with(dataStore) {
                            userToken = responseToken.accessToken ?: ""
                            refreshToken = responseToken.refreshToken ?: ""
                        }
                        Timber.tag("갱신된 토큰").d("accessToken: ${dataStore.userToken}, refreshToken: ${dataStore.refreshToken}")

                        refreshTokenResponse.close()
                        val newRequest = originalRequest.newAuthBuilder().build()
                        return chain.proceed(newRequest)
                    }

                    with(dataStore) {
                        isLogin = false
                        userToken = ""
                        refreshToken = ""
                        userInfo = ""
                    }
                } catch (t: Throwable) {
                    Timber.e(t)
                    with(dataStore) {
                        isLogin = false
                        userToken = ""
                        refreshToken = ""
                        userInfo = ""
                    }
                }
            }
        }
        return response
    }
    private fun Request.newAuthBuilder() =
        this.newBuilder().addHeader(HEADER_AUTHORIZATION, dataStore.userToken)

    companion object {
        private const val CODE_TOKEN_EXPIRED = 401
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_REFRESH_TOKEN = "Authorization-refresh"
    }
}