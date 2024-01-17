package com.teumteum.data.model.request

import com.teumteum.domain.entity.MeetingArea
import com.teumteum.domain.entity.MoimRequestModel
import java.time.LocalDateTime

data class RequestMoim(
    val topic: String,
    val title: String,
    val introduction: String,
    val promiseDateTime: LocalDateTime,
    val numberOfRecruits: Int,
    val meetingArea: MeetingArea,
)

fun MoimRequestModel.toBody() =
    RequestMoim(
        topic, title, introduction, promiseDateTime, numberOfRecruits, meetingArea
    )
