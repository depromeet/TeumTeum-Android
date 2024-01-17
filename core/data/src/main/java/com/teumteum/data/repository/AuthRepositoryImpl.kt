package com.teumteum.data.repository

import com.teumteum.data.datasource.remote.RemoteAuthDataSource
import com.teumteum.domain.TeumTeumDataStore
import com.teumteum.domain.entity.SocialLoginResult
import com.teumteum.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: RemoteAuthDataSource,
    private val dataStore: TeumTeumDataStore
) : AuthRepository {
    override suspend fun getSocialLogin(provider: String, code: String): Result<SocialLoginResult> {
        return runCatching {
            dataSource.getSocialLogin(provider, code)
        }
    }

    override fun getAutoLogin(): Boolean = dataStore.isLogin

    override fun setAutoLogin(userToken: String, refreshToken: String) {
        dataStore.isLogin = true
        dataStore.userToken = userToken
        dataStore.refreshToken = refreshToken
    }

    override fun getIsFirstAfterInstall(): Boolean = dataStore.isFirstAfterInstall

    override fun setIsFirstAfterInstall(isFirst: Boolean) {
        dataStore.isFirstAfterInstall = isFirst
    }
}