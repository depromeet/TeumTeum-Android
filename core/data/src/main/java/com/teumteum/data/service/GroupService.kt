package com.teumteum.data.service

import com.teumteum.data.model.request.RequestReviewFriend
import com.teumteum.data.model.response.ResponseGroup
import com.teumteum.data.model.response.ResponseMeeting
import com.teumteum.data.model.response.ResponseReviewFriends
import com.teumteum.domain.entity.Meeting
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupService {
    @Multipart
    @POST("meetings")
    suspend fun postMoim(
        @Part("meetingRequest") meetingRequest: RequestBody,
        @Part images: List<MultipartBody.Part>
    ): ResponseMeeting


    @GET("meetings")
    suspend fun getGroups(
        @Query("size") size: Int = 20,
        @Query("page") page: Int,
        @Query("sort") sort: String = "id,desc",
        @Query("isOpen") isOpen: Boolean,
        @Query("topic") topic: String?,
        @Query("meetingAreaStreet") meetingAreaStreet: String?,
        @Query("participantUserId") participantUserId: Long?,
        @Query("searchWord") searchWord: String?
    ): ResponseGroup

    @POST("meetings/{meetingId}/participants")
    suspend fun postJoinGroup(
        @Path("meetingId") meetingId: Long
    ): ResponseMeeting

    @DELETE("meetings/{meetingId}/participants")
    suspend fun deleteGroupJoin(
        @Path("meetingId") meetingId: Long
    ): Response<Void>

    @DELETE("meetings/{meetingId}")
    suspend fun deleteMeeting(
        @Path("meetingId") meetingId: Long
    ): Response<Void>

    @GET("meetings/{meetingsId}")
    suspend fun getGroup(
        @Path("meetingsId") meetingsId: Long
    ): ResponseMeeting

    @Multipart
    @PUT("meetings/{meetingId}")
    suspend fun modifyMeeting(
        @Path("meetingId") meetingId: Long,
        @Part images: List<MultipartBody.Part>,
        @Part("request") meetingRequest: RequestBody
    ): ResponseMeeting

    @POST("meeting/{meetingId}/reports")
    suspend fun reportMeeting(
        @Path("meetingId") meetingId:Long
    ): Response<Void>

    @GET("meetings/{meetingId}/participants")
    suspend fun getReviewFriendList(
        @Path("meetingId") meetingId: Long
    ): ResponseReviewFriends

    @POST("users/reviews")
    suspend fun postRegisterReview(
        @Query("meetingId") meetingsId: Long,
        @Body request: List<RequestReviewFriend>
    ):Response<Void>
}