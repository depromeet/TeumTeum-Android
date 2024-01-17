package com.teumteum.domain.repository

import com.teumteum.domain.entity.SocialLoginResult

interface AuthRepository {
    suspend fun getSocialLogin(provider: String, code: String): Result<SocialLoginResult>
    fun setAutoLogin(userToken: String, refreshToken: String)
    fun getAutoLogin(): Boolean
    fun setIsFirstAfterInstall(isFirst: Boolean)
    fun getIsFirstAfterInstall(): Boolean
}