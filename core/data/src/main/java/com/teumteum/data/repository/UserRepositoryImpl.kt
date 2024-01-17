package com.teumteum.data.repository

import com.teumteum.data.datasource.remote.RemoteUserDataSource
import com.teumteum.data.model.request.RequestUserInfo
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
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
    ): Result<Long> {
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