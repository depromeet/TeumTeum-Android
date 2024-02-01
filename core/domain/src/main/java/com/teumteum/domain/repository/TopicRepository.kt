package com.teumteum.domain.repository

import com.teumteum.domain.entity.TopicResponse

interface TopicRepository {
    suspend fun getTopics(userIds: List<String>, type: String): Result<TopicResponse>
}