package com.teumteum.data.model.response

import com.teumteum.domain.entity.Review
import kotlinx.serialization.Serializable

@Serializable
data class ResponseReview(
    val review: String,
    val count: Int
) {
    @Serializable
    data class ResponseReviewData(
        val reviews: List<ResponseReview>
    )
}

fun responseToReview(responseReviews: List<ResponseReview>): List<Review> {
    return responseReviews.map { responseReview ->
        Review(
            review = responseReview.review,
            count = responseReview.count
        )
    }
}