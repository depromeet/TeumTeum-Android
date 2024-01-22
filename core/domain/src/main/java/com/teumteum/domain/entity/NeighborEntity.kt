package com.teumteum.domain.entity

import kotlinx.serialization.SerialName

data class NeighborEntity(
    val characterId: Int,
    val id: Int,
    val jobDetailClass: String,
    val name: String
)
