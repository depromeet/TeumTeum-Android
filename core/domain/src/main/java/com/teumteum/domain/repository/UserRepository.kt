package com.teumteum.domain.repository

import com.teumteum.domain.entity.SignUpResult
import com.teumteum.domain.entity.UserInfo

interface UserRepository {
    suspend fun postUserInfo(
        user: UserInfo,
        oauthId: String,
        serviceAgreed: Boolean,
        privatePolicyAgreed: Boolean
        ): Result<SignUpResult>

    suspend fun getMyInfoFromServer(): Result<UserInfo>
    fun saveUserInfo(userInfo: UserInfo)
    fun getUserInfo(): UserInfo?
    fun deleteUserInfo()
}