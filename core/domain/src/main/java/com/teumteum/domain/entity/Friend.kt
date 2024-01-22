package com.teumteum.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    val id: Int,
    val characterId: Int,
    val name: String,
    val job: JobEntity
)
