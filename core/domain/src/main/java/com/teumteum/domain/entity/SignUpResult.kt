package com.teumteum.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResult (
    val id: Long,
    val accessToken: String,
    val refreshToken: String
)