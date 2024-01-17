package com.teumteum.data.service

import com.teumteum.data.model.response.ResponseGroup
import com.teumteum.data.model.response.ResponseMeeting
import com.teumteum.domain.entity.MoimRequestModel
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupService {

    @POST("meetings")
    suspend fun postMoim(
        @Body requestModel: MoimRequestModel
    ): Response


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
}