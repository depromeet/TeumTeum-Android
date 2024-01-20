package com.teumteum.teumteum.util.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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
