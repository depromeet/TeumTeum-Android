package com.teumteum.domain.entity

data class UserInfo (
    val id: Long,
    val name: String,
    val characterId: Int,
    val activityArea: ActivityArea,
    val mbti: String,
    val status: String,
    val job: JobEntity,
    val interests: List<String>,
    val goal: String
)

data class ActivityArea(
    val city: String,
    val street: List<String>
)

data class JobEntity (
    val name: String?, // Nullable
    val jobClass: String,
    val detailClass: String
)