package com.teumteum.data.model.response

import com.teumteum.domain.entity.JobEntity
import com.teumteum.domain.entity.UserInfo

data class ResponseUserInfo (
    val id: Long,
    val name: String,
    val birth: String,
    val characterId: Int,
    val mannerTemperature: Int,
    val authenticated: String,
    val activityArea: String,
    val mbti: String,
    val status: String,
    val goal: String,
    val job: JobEntity,
    val interests: List<String>
) {
    fun toUserInfo(): UserInfo {
        return UserInfo(id, name, characterId, activityArea, mbti, status, job, interests, goal)
    }

    // 매너온도와 로그인 한 소셜 (카카오 or 네이버) 정보는 필요 기능 구현 시 따로 리턴 필요
}