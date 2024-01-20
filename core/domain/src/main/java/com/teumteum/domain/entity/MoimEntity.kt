package com.teumteum.domain.entity

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
data class MoimEntity(
    val topic: String,
    val title: String,
    val introduction: String,
    val promiseDateTime: LocalDateTime,
    val numberOfRecruits: Int,
    val meetingArea: MeetingArea,
)


data class MeetingArea(
//    val mainStreet: String,
    val address: String,
    val addressDetail: String
)