package com.teumteum.teumteum.presentation.mypage.setting.viewModel

import android.content.Intent
import androidx.annotation.DrawableRes
import com.teumteum.domain.entity.Friend
import com.teumteum.teumteum.R

data class Meeting(
    val title: String,
    val time: String
)

data class Recommend(
    val id: Int,
    val name: String,
    val jobName: String? = null,
    val characterId: Int,
)

fun Friend.toRecommend(): Recommend {
    return Recommend(
        id = this.id,
        characterId = this.characterId,
        name =  this.name,
        jobName = this.job.name
    )
}


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

val MeetingDummy1 = Meeting("UX 북스터디", "1월 9일 오후 7:00")

val UserGradeDummy = listOf(
    UserGrade("Excellent", "최고에요!", 3, R.drawable.ic_grade_exel),
    UserGrade("Good", "좋아요!", 2, R.drawable.ic_grade_good),
    UserGrade("Bad", "별로에요...",1, R.drawable.ic_grade_bad),
)

val SignOutList = listOf(
    "쓰지 않는 앱이에요",
    "모임 목적과 맞지 않은 사용자가 많아요",
    "오류가 생겨서 쓸 수 없어요",
    "개인정보가 불안해요",
    "앱 사용법을 모르겠어요",
    "기타"
)

