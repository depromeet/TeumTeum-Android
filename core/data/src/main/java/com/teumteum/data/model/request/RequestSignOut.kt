package com.teumteum.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestSignOut(
    val withdrawReasons: List<String>
)
