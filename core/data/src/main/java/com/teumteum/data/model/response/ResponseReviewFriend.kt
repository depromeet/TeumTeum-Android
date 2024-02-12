package com.teumteum.data.model.response

import com.teumteum.domain.entity.ReviewFriend
import kotlinx.serialization.Serializable

@Serializable
data class ResponseReviewFriends(
    val participants: List<ResponseReviewFriend>
)

@Serializable
data class ResponseReviewFriend(
    val id: Long,
    val characterId: Long,
    val name: String,
    val job: String
) {
    fun toReviewFriend(): ReviewFriend {
        return ReviewFriend(id, characterId, name, job)
    }
}