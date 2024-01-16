package com.teumteum.domain.entity

data class AuthTokenModel (
    val accessToken: String,
    val refreshToken: String
)