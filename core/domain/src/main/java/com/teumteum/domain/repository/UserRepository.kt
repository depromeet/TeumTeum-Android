package com.teumteum.domain.repository

import com.teumteum.domain.entity.UserInfo

interface UserRepository {
    suspend fun postUserInfo(
        user: UserInfo,
        oauthId: String,
        serviceAgreed: Boolean,
        privatePolicyAgreed: Boolean,
        birth: String
        ): Result<Long>
    suspend fun getMyUserInfo(accessToken: String): Result<UserInfo>
}