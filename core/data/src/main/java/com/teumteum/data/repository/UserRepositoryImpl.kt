package com.teumteum.data.repository

import com.teumteum.domain.TeumTeumDataStore
import com.teumteum.data.datasource.remote.RemoteUserDataSource
import com.teumteum.data.model.request.RequestUserInfo
import com.teumteum.domain.entity.SignUpResult
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataStore: TeumTeumDataStore,
    private val dataSource: RemoteUserDataSource
) : UserRepository {
    override fun getUserInfo(): String {
        return dataStore.userInfo
    }

    override fun saveUserInfo(userInfo: String) {
        dataStore.userInfo = userInfo
    }

    override suspend fun getMyInfoFromServer(): Result<UserInfo> {
        return runCatching {
            dataSource.getMyUserInfo()
        }
    }

    override suspend fun postUserInfo(
        user: UserInfo,
        oauthId: String,
        serviceAgreed: Boolean,
        privatePolicyAgreed: Boolean
    ): Result<SignUpResult> {
        return runCatching {
            dataSource.postUserInfo(RequestUserInfo(
                userInfo = user,
                oauthId = oauthId,
                serviceAgreed = serviceAgreed,
                privatePolicyAgreed = privatePolicyAgreed
            ).getRequestUserInfoWithOAuthId())
        }
    }
}