package com.teumteum.data.datasource.remote

import com.teumteum.data.model.request.RequestUserInfoWithOAuthId
import com.teumteum.data.model.response.ResponseUserInfo
import com.teumteum.data.service.UserService
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val service: UserService
) {
    suspend fun getMyUserInfo(
        accessToken: String
    ): ResponseUserInfo {
        return service.getMyUserInfo(accessToken)
    }

    suspend fun postUserInfo(
        userInfo: RequestUserInfoWithOAuthId
    ): Long {
        return service.postUserInfo(userInfo)
    }
}