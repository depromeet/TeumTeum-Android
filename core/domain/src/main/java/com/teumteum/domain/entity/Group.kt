package com.teumteum.domain.entity

data class Group(
    val id: Long,
    val hostId: Long,
    val topic: String,
    val name: String,
    val introduction: String,
    val photoUrls: List<String>,
    val date: String
)