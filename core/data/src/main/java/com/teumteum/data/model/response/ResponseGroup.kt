package com.teumteum.data.model.response

import com.teumteum.data.BuildConfig.IMAGE_URL
import com.teumteum.domain.entity.Meeting
import com.teumteum.domain.entity.MeetingArea
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

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
        val photoUrlList = photoUrls.map {
            IMAGE_URL + it
        }
        return Meeting(id, hostId, topic, title, introduction, photoUrlList, promiseDateTime)
    }
}

