package com.teumteum.domain.entity

data class Message(
    var title: String,
    var body: String,
    var type: String,
    var meetingId: Int? = -1,
    var participants: List<Int>? = listOf()
): java.io.Serializable