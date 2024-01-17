package com.teumteum.domain.repository

import com.teumteum.domain.entity.SocialLoginResult

interface AuthRepository {
    suspend fun getSocialLogin(provider: String, code: String): Result<SocialLoginResult>
}