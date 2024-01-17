package com.teumteum.domain.entity

data class SignUpResult (
    val id: Long,
    val accessToken: String,
    val refreshToken: String
)