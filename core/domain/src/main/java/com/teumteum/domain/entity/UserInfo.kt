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
    val interests: List<String>,
    val friends: Int
) {
    constructor() : this(0, "", "", 0, 0, "", "",
        "", "", "", JobEntity(), listOf(), 0)

    fun isInvaild(): Boolean = name.isBlank()

    fun isIdentical(other: UserInfo): Boolean {
        return (name == other.name
                && birth == other.birth
                && characterId == other.characterId
                && activityArea == other.activityArea
                && mbti == other.mbti
                && status == other.status
                && goal == other.goal
                && job.detailClass == other.job.detailClass
                && job.name == other.job.name
                && interests.containsAll(other.interests)
                && other.interests.containsAll(interests))
    }
}

@Serializable
data class JobEntity(
    val name: String?,
    val certificated: Boolean,
    @SerialName("class")
    val jobClass: String,
    val detailClass: String
) {
    constructor() : this("null", false, "", "")
}


@Serializable
data class updatedUserInfo(
    val id: Long,
    val newName: String,
    val newBirth: String, // YYYYMMDD
    val newCharacterId: Long,
    val newMannerTemperature: Int,
    val authenticated: String,
    val newActivityArea: String,
    val newMbti: String,
    val newStatus: String,
    val newGoal: String,
    val newJob: JobEntity,
    val newInterests: List<String>,
    val friends: Int
)

fun UserInfo.toUpdatedUserInfo(): updatedUserInfo {
    return updatedUserInfo(
        id = this.id,
        newName = this.name,
        newBirth = this.birth,
        newCharacterId = this.characterId,
        newMannerTemperature = this.mannerTemperature,
        authenticated = this.authenticated,
        newActivityArea = this.activityArea,
        newMbti = this.mbti,
        newStatus = this.status,
        newGoal = this.goal,
        newJob = this.job,
        newInterests = this.interests,
        friends = this.friends
    )
}
