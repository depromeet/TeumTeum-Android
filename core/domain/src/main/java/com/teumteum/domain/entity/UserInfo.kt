package com.teumteum.domain.entity

data class UserInfo (
    val id: Long,
    val name: String,
    val characterId: Int,
    val activityArea: String,
    val mbti: String,
    val status: String,
    val job: JobEntity,
    val interests: List<String>,
    val goal: String
) {
    fun changeUserInfoId(newId: Long): UserInfo {
        return UserInfo(newId, name, characterId, activityArea, mbti, status, job, interests, goal)
    }
}

data class JobEntity (
    val name: String?, // Nullable
    val jobClass: String,
    val detailClass: String
)