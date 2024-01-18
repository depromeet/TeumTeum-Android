package com.teumteum.domain.repository

interface UserRepository {
    fun saveUserInfo(userInfo: String)
    fun getUserInfo(): String
import com.teumteum.domain.entity.SignUpResult
import com.teumteum.domain.entity.UserInfo

interface UserRepository {
    suspend fun postUserInfo(
        user: UserInfo,
        oauthId: String,
        serviceAgreed: Boolean,
        privatePolicyAgreed: Boolean,
        birth: String
        ): Result<SignUpResult>
    suspend fun getMyUserInfo(): Result<UserInfo>
}