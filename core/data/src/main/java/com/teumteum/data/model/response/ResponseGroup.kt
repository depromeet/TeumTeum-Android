package com.teumteum.data.model.response

import com.teumteum.domain.entity.Meeting

data class ResponseGroup(
    val data: ResponseGroupData,
    val hasNext: Boolean
) {
    data class ResponseGroupData(
        val meetings: List<ResponseMeeting>
    )
}
data class ResponseMeeting(
    val hostId: Long,
    val id: Long,
    val introduction: String,
    val meetingArea: ResponseMeetingArea,
    val numberOfRecruits: Int,
    val participantIds: List<Int>,
    val photoUrls: List<String>,
    val promiseDateTime: String,
    val title: String,
    val topic: String
) {
    fun toMeeting(): Meeting {
        return Meeting(id, hostId, topic, title, introduction, photoUrls, promiseDateTime)
    }
}