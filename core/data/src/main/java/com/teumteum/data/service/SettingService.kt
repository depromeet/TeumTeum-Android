package com.teumteum.data.service

import com.teumteum.data.model.request.RequestSignOut
import com.teumteum.data.model.response.ResponseMyMeeting
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SettingService {
    @POST("users/logouts")
    suspend fun logOut(): Response<Unit>

    @GET("meetings")
    suspend fun getBookmarkMeeting(
        @Query("size") size: Int = 20,
        @Query("page") page: Int,
        @Query("sort") sort: String = "promiseDateTime",
        @Query("isOpen") isOpen: Boolean,
        @Query("isBookmarked") isBookmarked: Boolean
    ): ResponseMyMeeting


    @POST("users/withdraws")
    suspend fun signOut(@Body request: RequestSignOut): Response<Unit>

    @GET("meetings")
    suspend fun getMyPageOpenMeeting(
        @Query("size") size: Int = 20,
        @Query("page") page: Int,
        @Query("sort") sort: String = "promiseDateTime",
        @Query("isOpen") isOpen: Boolean,
        @Query("participantUserId") participantUserId: Long?,
    ): ResponseMyMeeting

    @GET("meetings")
    suspend fun getMyPageClosedMeeting(
        @Query("size") size: Int = 20,
        @Query("page") page: Int,
        @Query("sort") sort: String = "promiseDateTime",
        @Query("isOpen") isOpen: Boolean,
        @Query("participantUserId") participantUserId: Long?,
    ): ResponseMyMeeting
}