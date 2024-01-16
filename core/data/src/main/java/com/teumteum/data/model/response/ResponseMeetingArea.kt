package com.teumteum.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMeetingArea(
    val address: String,
    val addressDetail: String,
    val mainStreet: String
)