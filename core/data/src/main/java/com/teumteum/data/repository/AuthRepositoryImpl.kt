package com.teumteum.data.repository

import com.teumteum.data.datasource.remote.RemoteAuthDataSource
import com.teumteum.domain.entity.AuthTokenModel
import com.teumteum.domain.entity.SocialLoginResult
import com.teumteum.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: RemoteAuthDataSource
) : AuthRepository {
    override suspend fun getSocialLogin(provider: String, code: String): Result<SocialLoginResult> {
        return runCatching {
            dataSource.getSocialLogin(provider, code)
        }
    }

    override suspend fun postReissueToken(
        token: AuthTokenModel
    ): Result<AuthTokenModel> {
        return runCatching {
            dataSource.postReissueToken(token)
        }
    }
}