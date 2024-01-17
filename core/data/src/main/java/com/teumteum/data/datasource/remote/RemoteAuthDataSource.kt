package com.teumteum.data.datasource.remote

import com.teumteum.data.service.AuthService
import com.teumteum.domain.entity.SocialLoginResult
import javax.inject.Inject

class RemoteAuthDataSource @Inject constructor(
    private val service: AuthService
) {
    suspend fun getSocialLogin(provider: String, code: String): SocialLoginResult {
        return service.getSocialLogin(provider, code)
    }
}