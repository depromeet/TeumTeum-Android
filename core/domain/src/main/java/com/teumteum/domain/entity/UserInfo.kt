package com.teumteum.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo (
    val id: Long,
    val name: String,
    val birth: String, // YYYYMMDD
    val characterId: Long,
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
    @SerialName("class")
    val jobClass: String,
    val detailClass: String
)