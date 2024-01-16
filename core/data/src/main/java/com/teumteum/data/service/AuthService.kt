package com.teumteum.data.service

import com.teumteum.domain.entity.AuthTokenModel
import com.teumteum.domain.entity.SocialLoginResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {
    @POST("auth/reissues")
    suspend fun postReissueToken(
        @Header("Authorization") accessToken: String,
        @Header("Authorization-refresh") refreshToken: String
    ): AuthTokenModel

    @GET("login/callbacks/{provider}")
    suspend fun getSocialLogin(
        @Path("provider") provider: String,
        @Query("code") code: String
    ): SocialLoginResult
}