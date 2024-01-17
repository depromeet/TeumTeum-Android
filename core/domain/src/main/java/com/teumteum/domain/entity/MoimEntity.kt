package com.teumteum.domain.entity

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class MoimEntity(
    val id : Long,
    val hostId : Long,
    val topic : String,
    val title: String,
    val introduction : String,
    val imageUrls : List<String>,
    val promiseDateTime: LocalDateTime,
    val numberOfRecruits: Integer,
    val meetingArea: MeetingArea,
    val participantIds: List<Int>
)

@Serializable
data class MoimRequestModel(
    val topic: String,
    val title: String,
    val introduction: String,
    val promiseDateTime: LocalDateTime,
    val numberOfRecruits: Int,
    val meetingArea: MeetingArea,
)


data class MeetingArea(
    val mainStreet: String,
    val address: String,
    val addressDetail: String
)