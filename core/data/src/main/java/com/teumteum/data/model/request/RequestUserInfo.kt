package com.teumteum.data.model.request

import com.teumteum.domain.entity.UserInfo
import kotlinx.serialization.Serializable

@Serializable
data class RequestUserInfoWithOAuthId (
    val id: String,
    val terms: AgreedTerms,
    val name: String,
    val birth: String,
    val characterId: Long,
    val authenticated: String,
    val activityArea: String,
    val mbti: String,
    val status: String,
    @Serializable
    val job: JobEntity,
    val interests: List<String>,
    val goal: String
) {

    @Serializable
    data class AgreedTerms(
        val service: Boolean,
        val privatePolicy: Boolean
    )
}

data class RequestUserInfo(
    val userInfo: UserInfo,
    val oauthId: String,
    val serviceAgreed: Boolean,
    val privatePolicyAgreed: Boolean
) {
    fun getRequestUserInfoWithOAuthId()
    : RequestUserInfoWithOAuthId {
        val result = RequestUserInfoWithOAuthId(
            id = oauthId,
            terms = RequestUserInfoWithOAuthId.AgreedTerms(serviceAgreed, privatePolicyAgreed),
            name = userInfo.name,
            birth = userInfo.birth,
            characterId = userInfo.characterId,
            authenticated = userInfo.authenticated,
            activityArea = userInfo.activityArea,
            mbti = userInfo.mbti,
            status = userInfo.status,
            job = JobEntity(userInfo.job.name, userInfo.job.jobClass, userInfo.job.detailClass),
            interests = userInfo.interests,
            goal = userInfo.goal
        )
        return result
    }
}

@Serializable
data class JobEntity(
    val name: String?,
    val `class`: String,
    val detailClass: String
)