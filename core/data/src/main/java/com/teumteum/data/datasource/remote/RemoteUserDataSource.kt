package com.teumteum.data.datasource.remote

import com.teumteum.data.model.request.RequestDeviceToken
import com.teumteum.data.model.request.RequestUserInfoWithOAuthId
import com.teumteum.data.model.response.ResponseReview
import com.teumteum.data.model.response.ResponseReviewData
import com.teumteum.data.service.UserService
import com.teumteum.domain.entity.Alerts
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.FriendRecommend
import com.teumteum.domain.entity.SignUpResult
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.entity.Users
import com.teumteum.domain.entity.updatedUserInfo
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val service: UserService
) {
    suspend fun getMyUserInfo(): UserInfo {
        return service.getMyUserInfo()
    }

    suspend fun getFriendInfo(userId: Long): UserInfo {
        return service.getFriendInfo(userId)
    }

    suspend fun postUserInfo(
        userInfo: RequestUserInfoWithOAuthId
    ): SignUpResult {
        return service.postUserInfo(userInfo)
    }

    suspend fun getUserFriend(userId: Long): FriendRecommend {
        return service.getUserFriends(userId)
    }

    suspend fun getUser(userId: Long): Friend {
        return service.getUser(userId)
    }

    suspend fun getUsers(id: String): Users {
        return service.getUsers(id)
    }

    suspend fun updateUserInfo(
        userInfo: updatedUserInfo
    ): Boolean {
        return service.updateUserInfo(userInfo).isSuccessful
    }

    suspend fun postFriend(
        userId: Long
    ): Boolean {
        return service.postFriend(userId).isSuccessful
    }

    suspend fun postDeviceToken(token: RequestDeviceToken): Boolean {
        return service.postDeviceToken(token).isSuccessful
    }

    suspend fun patchDeviceToken(token: RequestDeviceToken): Boolean {
        return service.patchDeviceToken(token).isSuccessful
    }

    suspend fun getAlerts(): Alerts {
        return service.getAlerts()
    }

    suspend fun getUserReview(userId: Long): ResponseReviewData {
        return service.getUserReview(userId)
    }
}