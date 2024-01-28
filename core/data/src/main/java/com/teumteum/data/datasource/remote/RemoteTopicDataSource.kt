package com.teumteum.data.datasource.remote

import com.teumteum.data.service.TopicService
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class RemoteTopicDataSource @Inject constructor(private val topicService: TopicService) {
    suspend fun getTopics(userIds: List<String>, type: String): Response<ResponseBody> {
        return topicService.getTopics(userIds, type)
    }
}