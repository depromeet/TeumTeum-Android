package com.teumteum.data.model.response

import com.teumteum.domain.entity.Meeting
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGroup(
    val data: ResponseGroupData,
    val hasNext: Boolean
) {
    @Serializable
    data class ResponseGroupData(
        val meetings: List<ResponseMeeting>
    )
}
@Serializable
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