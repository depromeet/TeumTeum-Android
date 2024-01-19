package com.teumteum.teumteum.presentation.mypage

import androidx.annotation.DrawableRes
import com.teumteum.teumteum.R

data class Meeting(
    val title: String,
    val time: String
)

data class Recommend(
    val name: String,
    val jobName: String,
    @DrawableRes val image: Int
)

data class UserGrade(
    val grade: String,
    val text: String,
    val count: Int,
    @DrawableRes val image:Int
)


data class SettingUiItem(
    val title: String,
    val url: String = "",
    val onClick: () -> Unit = {}
)

val MeetingDummy = listOf(
    Meeting("UX 북스터디", "1월 9일 오후 7:00"),
    Meeting("프로덕트 디자이너 포폴 리뷰 세션", "1월 8일 오후 6:00"),
    Meeting("커피 마시며 고민 말하기", "1월 7일 오후 9:00"),
)

val UserGradeDummy = listOf(
    UserGrade("Excellent", "최고에요!", 3, R.drawable.ic_grade_exel),
    UserGrade("Good", "좋아요!", 2, R.drawable.ic_grade_good),
    UserGrade("Bad", "별로에요...",1, R.drawable.ic_grade_bad),
)

val RecommendDummy = listOf(
    Recommend("김예은", "프로덕트 디자이너", R.drawable.ic_dog),
    Recommend("신민서", "AOS 개발자", R.drawable.ic_cat),
    Recommend("신한별", "프로덕트 디자이너", R.drawable.ic_fox),
    Recommend("정은아", "프로덕트 디자이너", R.drawable.ic_ghost),

)

val SignOutList = listOf(
    "쓰지 않는 앱이에요",
    "모임 목적과 맞지 않은 사용자가 많아요",
    "오류가 생겨서 쓸 수 없어요",
    "개인정보가 불안해요",
    "앱 사용법을 모르겠어요",
    "기타"
)


fun getMemberSetting(): List<SettingUiItem> {
    return listOf(
        SettingUiItem(title = "약관 및 개인정보 처리 동의", onClick = { }),
        SettingUiItem(title = "탈퇴하기", onClick = {  }),
        SettingUiItem(title = "로그아웃", onClick = { })
    )
}

fun getServiceGuide(): List<SettingUiItem> {
    return listOf(
        SettingUiItem(title = "서비스 이용약관", url = "https://sheer-billboard-d63.notion.site/KUSITMS-9e6619383bcd4ce68b6ba4b2b6ef0d40?pvs=4"),
        SettingUiItem(title = "개인정보 처리방침", url = "https://sheer-billboard-d63.notion.site/24a4639559d4433cb89c8f1abb889726?pvs=4")
    )
}