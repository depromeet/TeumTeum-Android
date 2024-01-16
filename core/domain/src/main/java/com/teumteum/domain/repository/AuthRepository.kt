package com.teumteum.domain.repository

import com.teumteum.domain.entity.AuthTokenModel
import com.teumteum.domain.entity.SocialLoginResult

interface AuthRepository {
    suspend fun postReissueToken(token: AuthTokenModel): Result<AuthTokenModel>
    suspend fun getSocialLogin(provider: String, code: String): Result<SocialLoginResult>
}