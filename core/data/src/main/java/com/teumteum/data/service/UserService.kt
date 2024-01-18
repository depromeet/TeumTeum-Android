package com.teumteum.data.service

import com.teumteum.data.model.request.RequestUserInfoWithOAuthId
import com.teumteum.domain.entity.SignUpResult
import com.teumteum.domain.entity.UserInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("users/me")
    suspend fun getMyUserInfo(): UserInfo

    @POST("users")
    suspend fun postUserInfo(
        @Body userInfo: RequestUserInfoWithOAuthId
    ): SignUpResult
}