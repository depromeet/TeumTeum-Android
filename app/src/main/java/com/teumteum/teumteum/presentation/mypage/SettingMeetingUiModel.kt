package com.teumteum.teumteum.presentation.mypage

data class SettingMeetingUiModel(
    val title: String,
    val time: String
)

data class Meeting(
    val title: String,
    val time: String
)

val SignOutList = listOf(
    "쓰지 않는 앱이에요",
    "모임 목적과 맞지 않은 사용자가 많아요",
    "오류가 생겨서 쓸 수 없어요",
    "개인정보가 불안해요",
    "앱 사용법을 모르겠어요",
    "기타"
)