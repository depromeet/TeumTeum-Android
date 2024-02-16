package com.teumteum.data.model.request

import com.teumteum.domain.entity.ReviewFriend
import kotlinx.serialization.Serializable

@Serializable
data class RequestReviewFriends(
    val reviews: List<RequestReviewFriend>
)

@Serializable
data class RequestReviewFriend(
    val id: Long,
    val review: String
)

fun ReviewFriend.toRequestReviewFriend(): RequestReviewFriend {
    return RequestReviewFriend(id, review)
}