package com.teumteum.data.service

import com.teumteum.data.model.response.ResponseGetRecommendMeet
import com.teumteum.data.model.response.ResponseGetSample
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TopicService {
    @GET("/users/interests")
    suspend fun getTopics(
        @Query("user-id") userIds: List<String>,
        @Query("type") type: String
    ): Response<ResponseBody>
}