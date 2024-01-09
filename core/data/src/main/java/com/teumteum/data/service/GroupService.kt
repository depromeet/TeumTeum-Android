package com.teumteum.data.service

import com.teumteum.data.model.response.ResponseGetRecommendMeet
import com.teumteum.data.model.response.ResponseGroup
import retrofit2.http.GET
import retrofit2.http.Query

interface GroupService {
    @GET("meetings")
    suspend fun getGroups(
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Query("sort") sort: String,
        @Query("isOpen") isOpen: Boolean,
        @Query("topic") topic: String?,
        @Query("meetingAreaStreet") meetingAreaStreet: String?,
        @Query("participantUserId") participantUserId: Long?,
        @Query("searchWord") searchWord: String?
    ): ResponseGroup
}