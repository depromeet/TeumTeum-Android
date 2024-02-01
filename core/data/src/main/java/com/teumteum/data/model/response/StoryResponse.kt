package com.teumteum.data.model.response

import kotlinx.serialization.Serializable
@Serializable
data class StoryResponse(
    val topic: String,
    val story: String
)