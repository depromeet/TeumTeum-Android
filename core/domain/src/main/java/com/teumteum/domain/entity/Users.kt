package com.teumteum.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Users (
    val users: List<Friend>
)