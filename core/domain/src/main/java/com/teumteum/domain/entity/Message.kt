package com.teumteum.domain.entity

data class Message(
    var title: String,
    var body: String,
    var type: String,
    var meetingId: Long? = null,
    var participants: List<Int>? = null
): java.io.Serializable