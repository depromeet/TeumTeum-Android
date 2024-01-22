package com.teumteum.data.service

import com.teumteum.data.model.request.RequestUserInfo
import com.teumteum.data.model.request.RequestUserInfoWithOAuthId
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.Friends
import com.teumteum.domain.entity.SignUpResult
import com.teumteum.domain.entity.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @GET("users/me")
    suspend fun getMyUserInfo(): UserInfo

    @POST("users")
    suspend fun postUserInfo(
        @Body userInfo: RequestUserInfoWithOAuthId
    ): SignUpResult

    @GET("users/{userId}/friends")
    suspend fun getUserFriends(
        @Path("userId") userId: Long
    ): Friends

    @PUT("users")
    suspend fun updateUserInfo(
        @Body userInfo: UserInfo
    ): Response<Unit>

}

