package com.teumteum.data.datasource.remote

import com.teumteum.data.model.request.RequestUserInfo
import com.teumteum.data.model.request.RequestUserInfoWithOAuthId
import com.teumteum.data.service.UserService
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.Friends
import com.teumteum.domain.entity.SignUpResult
import com.teumteum.domain.entity.UserInfo
import retrofit2.Response
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val service: UserService
) {
    suspend fun getMyUserInfo(): UserInfo {
        return service.getMyUserInfo()
    }

    suspend fun postUserInfo(
        userInfo: RequestUserInfoWithOAuthId
    ): SignUpResult {
        return service.postUserInfo(userInfo)
    }

    suspend fun getUserFriend(): Friends {
        return service.getUserFriends()
    }

    suspend fun updateUserInfo(
        userInfo: UserInfo
    ): Boolean {
        return service.updateUserInfo(userInfo).isSuccessful
    }
}