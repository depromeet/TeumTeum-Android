package com.teumteum.data.datasource.remote

import com.teumteum.data.model.request.RequestUserInfoWithOAuthId
import com.teumteum.data.service.UserService
import com.teumteum.domain.entity.SignUpResult
import com.teumteum.domain.entity.UserInfo
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
        val result = service.postUserInfo(userInfo)
//        Timber.tag("teum-login").d("result: ${result}")
        return result
    }
}