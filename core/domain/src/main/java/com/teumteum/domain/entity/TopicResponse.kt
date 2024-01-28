package com.teumteum.domain.entity

sealed class TopicResponse {
    data class Balance(
        val topic: String,
        val balanceQuestion: List<String>
    ) : TopicResponse()

    data class Story(
        val topic: String,
        val story: String
    ) : TopicResponse()
}