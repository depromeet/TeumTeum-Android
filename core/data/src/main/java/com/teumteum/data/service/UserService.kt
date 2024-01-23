package com.teumteum.data.service

import com.teumteum.data.model.request.RequestUserInfoWithOAuthId
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.Friends
import com.teumteum.domain.entity.SignUpResult
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.entity.Users
import com.teumteum.domain.entity.updatedUserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: Long
    ): Friend

    @GET("users")
    suspend fun getUsers(
        @Query("id") id: String
    ): Users

    @PUT("users")
    suspend fun updateUserInfo(
        @Body userInfo: updatedUserInfo
    ): Response<Unit>

    @GET("users/{userId}")
    suspend fun getFriendInfo(
        @Path("userId") userId: Long
    ): UserInfo

    @POST("users/{userId}/friends")
    suspend fun postFriend(
        @Path("userId") userId: Long
    ): Response<Unit>

}

