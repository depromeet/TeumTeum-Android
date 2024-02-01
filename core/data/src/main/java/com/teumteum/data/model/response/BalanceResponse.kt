package com.teumteum.data.model.response

import kotlinx.serialization.Serializable
@Serializable
data class BalanceResponse(
    val topic: String,
    val balanceQuestion: List<String>
)