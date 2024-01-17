package com.teumteum.data.datasource.remote

import com.teumteum.data.model.request.RequestUserInfoWithOAuthId
import com.teumteum.data.model.response.ResponseUserInfo
import com.teumteum.data.service.UserService
import com.teumteum.domain.entity.SignUpResult
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val service: UserService
) {
    suspend fun getMyUserInfo(): ResponseUserInfo {
        return service.getMyUserInfo()
    }

    suspend fun postUserInfo(
        userInfo: RequestUserInfoWithOAuthId
    ): SignUpResult {
        return service.postUserInfo(userInfo)
    }
}