package com.teumteum.data.model.response

import com.teumteum.data.BuildConfig.IMAGE_URL
import com.teumteum.domain.entity.Meeting
import com.teumteum.domain.entity.MeetingArea
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

        val date = promiseDateTime.convertDateString()
        return Meeting(id, hostId, topic, title, introduction, photoUrlList, numberOfRecruits, date, participantIds, meetingArea.address, meetingArea.addressDetail)
    }
}

fun String.convertDateString(): String {
    // 입력된 문자열을 LocalDateTime으로 파싱
    val dateTime =
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
    // 출력 포맷 지정
    val formatter = DateTimeFormatter.ofPattern("MM월 dd일 a h:mm")
    val date = dateTime.format(formatter).replace("AM", "오전").replace("PM", "오후")
    // 포맷팅된 문자열 반환
    return date
}

