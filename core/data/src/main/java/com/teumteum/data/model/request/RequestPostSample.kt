package com.teumteum.data.model.request
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostSample(
    @SerialName("sample")
    val sample: String,
)