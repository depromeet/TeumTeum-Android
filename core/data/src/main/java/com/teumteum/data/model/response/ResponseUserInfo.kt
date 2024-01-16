package com.teumteum.data.model.response

import com.teumteum.domain.entity.ActivityArea
import com.teumteum.domain.entity.JobEntity
import com.teumteum.domain.entity.UserInfo

data class ResponseUserInfo (
    val id: Long,
    val name: String,
    val birth: String,
    val characterId: Int,
    val mannerTemperature: Int,
    val authenticated: String,
    val activityArea: ActivityArea,
    val mbti: String,
    val status: String,
    val goal: String,
    val job: JobEntity,
    val interests: List<String>
) {
    fun toUserInfo(): UserInfo {
        return UserInfo(id, name, characterId, activityArea, mbti, status, job, interests, goal)
    }
}