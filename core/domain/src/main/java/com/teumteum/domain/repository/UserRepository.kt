package com.teumteum.domain.repository

import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.SignUpResult
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.entity.Users

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
    suspend fun getUserFriends(userId:Long): Result<List<Friend>>
    suspend fun getUser(userId: Long): Result<Friend>
    suspend fun getUsers(id: String): Result<Users>
}