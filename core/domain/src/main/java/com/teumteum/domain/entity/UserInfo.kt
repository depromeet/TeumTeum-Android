package com.teumteum.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo (
    val id: Long,
    val name: String,
    val birth: String, // YYYY.MM.DD
    val characterId: Int,
    val mannerTemperature: Int,
    val authenticated: String,
    val activityArea: String,
    val mbti: String,
    val status: String,
    val goal: String,
    val job: JobEntity,
    val interests: List<String>
)

@Serializable
data class JobEntity(
    val name: String?,
    val `class`: String,
    val detailClass: String
)