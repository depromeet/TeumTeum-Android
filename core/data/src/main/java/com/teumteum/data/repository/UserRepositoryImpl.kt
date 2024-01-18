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
) : UserRepository {
    override fun getUserInfo(): String {
        return dataStore.userInfo
    }

    override fun saveUserInfo(userInfo: String) {
        dataStore.userInfo = userInfo
    private val dataSource: RemoteUserDataSource
) : UserRepository {
    override suspend fun getMyUserInfo(): Result<UserInfo> {
        return runCatching {
            dataSource.getMyUserInfo().toUserInfo()
        }
    }

    override suspend fun postUserInfo(
        user: UserInfo,
        oauthId: String,
        serviceAgreed: Boolean,
        privatePolicyAgreed: Boolean,
        birth: String
    ): Result<SignUpResult> {
        return runCatching {
            dataSource.postUserInfo(RequestUserInfo(
                userInfo = user,
                oauthId = oauthId,
                serviceAgreed = serviceAgreed,
                privatePolicyAgreed = privatePolicyAgreed,
                birth = birth
            ).getRequestUserInfoWithOAuthId())
        }
    }
}