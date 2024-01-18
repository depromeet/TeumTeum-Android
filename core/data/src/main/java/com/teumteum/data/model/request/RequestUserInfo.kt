package com.teumteum.data.model.request

import com.teumteum.domain.entity.JobEntity
import com.teumteum.domain.entity.UserInfo
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class RequestUserInfoWithOAuthId (
    val id: String,
    val terms: AgreedTerms,
    val name: String,
    val birth: String,
    val characterId: Int,
    val authenticated: String,
    val activityArea: String,
    val mbti: String,
    val status: String,
    @Contextual
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

@Serializable
data class RequestUserInfo(
    @Contextual
    val userInfo: UserInfo,
    val oauthId: String,
    val serviceAgreed: Boolean,
    val privatePolicyAgreed: Boolean
) {
    fun getRequestUserInfoWithOAuthId()
    : RequestUserInfoWithOAuthId {
        return RequestUserInfoWithOAuthId(
            id = oauthId,
            terms = RequestUserInfoWithOAuthId.AgreedTerms(serviceAgreed, privatePolicyAgreed),
            name = userInfo.name,
            birth = userInfo.birth,
            characterId = userInfo.characterId,
            authenticated = userInfo.authenticated,
            activityArea = userInfo.activityArea,
            mbti = userInfo.mbti,
            status = userInfo.status,
            job = userInfo.job,
            interests = userInfo.interests,
            goal = userInfo.goal
        )
    }
}