package com.teumteum.data.model.request

import com.teumteum.domain.entity.JobEntity
import com.teumteum.domain.entity.UserInfo

data class RequestUserInfoWithOAuthId (
    val id: String,
    val terms: AgreedTerms,
    val name: String,
    val birth: String,
    val characterId: Int,
    val activityArea: String,
    val mbti: String,
    val status: String,
    val job: JobEntity,
    val interests: List<String>,
    val goal: String
) {
    data class AgreedTerms(
        val service: Boolean,
        val privatePolicy: Boolean
    )
}

data class RequestUserInfo(
    val userInfo: UserInfo,
    val oauthId: String,
    val serviceAgreed: Boolean,
    val privatePolicyAgreed: Boolean,
    val birth: String
) {
    fun getRequestUserInfoWithOAuthId()
    : RequestUserInfoWithOAuthId {
        return RequestUserInfoWithOAuthId(
            oauthId,
            RequestUserInfoWithOAuthId.AgreedTerms(serviceAgreed, privatePolicyAgreed),
            userInfo.name,
            birth,
            userInfo.characterId,
            userInfo.activityArea,
            userInfo.mbti,
            userInfo.status,
            userInfo.job,
            userInfo.interests,
            userInfo.goal
        )
    }
}