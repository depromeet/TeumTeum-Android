package com.teumteum.data.model.response

import com.teumteum.domain.entity.Group

data class ResponseGroup(
    val data: ResponseGroupData,
    val hasNext: Boolean
) {
    data class ResponseGroupData(
        val meetings: List<ResponseMeeting>
    ) {
        data class ResponseMeeting(
            val hostId: Int,
            val id: Int,
            val introduction: String,
            val meetingArea: ResponseMeetingArea,
            val numberOfRecruits: Int,
            val participantIds: List<Int>,
            val photoUrls: List<String>,
            val promiseDateTime: String,
            val title: String,
            val topic: String
        ) {
            data class ResponseMeetingArea(
                val city: String,
                val street: String,
                val zipCode: String
            )
        }
    }
}