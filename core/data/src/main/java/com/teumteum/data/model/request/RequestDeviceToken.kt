package com.teumteum.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestDeviceToken(
    @SerialName("token")
    val deviceToken: String
)

fun String.toDeviceToken() : RequestDeviceToken {
    return RequestDeviceToken(this)
}