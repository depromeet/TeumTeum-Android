package com.teumteum.data.repository

import com.google.gson.Gson
import com.teumteum.data.datasource.remote.RemoteTopicDataSource
import com.teumteum.domain.entity.TopicResponse
import com.teumteum.domain.repository.TopicRepository
import javax.inject.Inject

class TopicRepositoryImpl @Inject constructor(
    private val remoteTopicDataSource: RemoteTopicDataSource
) : TopicRepository {
    override suspend fun getTopics(userIds: List<String>, type: String): Result<TopicResponse> {
        return try {
            val response = remoteTopicDataSource.getTopics(userIds, type)
            if (response.isSuccessful) {
                val responseBodyString = response.body()?.string()
                val topicResponse = when (type) {
                    "balance" -> Gson().fromJson(
                        responseBodyString,
                        TopicResponse.Balance::class.java
                    )

                    "story" -> Gson().fromJson(responseBodyString, TopicResponse.Story::class.java)
                    else -> throw IllegalStateException("Unknown type: $type")
                }
                Result.success(topicResponse)
            } else {
                Result.failure(RuntimeException("API call failed with status code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}