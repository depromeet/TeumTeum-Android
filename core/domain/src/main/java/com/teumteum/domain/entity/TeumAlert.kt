package com.teumteum.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class TeumAlert (
    val title: String,
    val body: String,
    val type: String,
    val createdAt: String,
    val isRead: Boolean
)