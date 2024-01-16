package com.teumteum.data.datasource.remote

import com.teumteum.data.service.AuthService
import com.teumteum.domain.entity.AuthTokenModel
import com.teumteum.domain.entity.SocialLoginResult
import javax.inject.Inject

class RemoteAuthDataSource @Inject constructor(
    private val service: AuthService
) {
    suspend fun postReissueToken(token: AuthTokenModel): AuthTokenModel {
        return service.postReissueToken(token.accessToken, token.refreshToken)
    }

    suspend fun getSocialLogin(provider: String, code: String): SocialLoginResult {
        return service.getSocialLogin(provider, code)
    }
}