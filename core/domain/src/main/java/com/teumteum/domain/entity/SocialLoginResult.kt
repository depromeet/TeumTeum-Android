package com.teumteum.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginResult (
    val accessToken: String?,
    val refreshToken: String?,
    val oauthId: String?,
    val messages: String?
)