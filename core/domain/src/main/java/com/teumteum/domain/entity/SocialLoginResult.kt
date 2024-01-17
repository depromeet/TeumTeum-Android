package com.teumteum.domain.entity

data class SocialLoginResult (
    val accessToken: String?,
    val refreshToken: String?,
    val oauthId: String?
) {
    val isAlreadyMember: Boolean = oauthId.isNullOrEmpty()
}