package com.teumteum.data.model.response

import com.teumteum.domain.entity.RecommendMeetEntity
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetRecommendMeet(
    val sample: String,
)

fun ResponseGetRecommendMeet.toEntity(): RecommendMeetEntity {
    return RecommendMeetEntity(sample = sample)
}