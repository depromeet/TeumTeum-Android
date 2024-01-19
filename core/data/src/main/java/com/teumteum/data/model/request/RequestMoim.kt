package com.teumteum.data.model.request

import com.teumteum.domain.entity.MeetingArea
import com.teumteum.domain.entity.MoimRequestModel
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class RequestMoim(
    val topic: String,
    val title: String,
    val introduction: String,
    val promiseDateTime: String,
    val numberOfRecruits: Int,
    val meetingArea: MeetingArea,
)

fun MoimRequestModel.toBody() =
    RequestMoim(
        topic,
        title,
        introduction,
        promiseDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        numberOfRecruits,
        meetingArea
    )
