package com.teumteum.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReviewFriend(
    val id: Long,
    val characterId: Long,
    val name: String,
    val job: String,
    var isSelected: Boolean = false,
    var review: String = ""
)