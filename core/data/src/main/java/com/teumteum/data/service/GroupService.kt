package com.teumteum.data.service

import com.teumteum.data.model.request.RequestMoim
import com.teumteum.data.model.response.ResponseGroup
import com.teumteum.data.model.response.ResponseMeeting
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
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
    suspend fun postGroupJoin(
        @Path("meetingId") meetingId: Long
    ): ResponseMeeting

    @DELETE("meetings/{meetingId}/participants")
    suspend fun deleteGroupJoin(
        @Path("meetingId") meetingId: Long
    ): Response

    @GET("meetings/{meetingsId}")
    suspend fun getGroup(
        @Path("meetingsId") meetingsId: Long
    ): ResponseMeeting
}