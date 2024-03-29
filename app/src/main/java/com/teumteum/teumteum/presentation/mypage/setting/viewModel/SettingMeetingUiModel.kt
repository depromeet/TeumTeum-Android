package com.teumteum.teumteum.presentation.mypage.setting.viewModel

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.compose.ui.res.stringResource
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.FriendMyPage
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

fun FriendMyPage.toRecommend(): Recommend {
    return Recommend(
        id = this.id,
        characterId = this.characterId,
        name =  this.name,
        jobName = this.job.name
    )
}


data class UserGrade(
    val text: String,
    val count: Int,
    @DrawableRes val image:Int
)

val emptyUserGrade = listOf(
    UserGrade( "최고예요", 0, R.drawable.ic_grade_exel),
    UserGrade( "좋아요", 0, R.drawable.ic_grade_good),
    UserGrade( "별로예요", 0, R.drawable.ic_grade_bad),
)


data class SettingUiItem(
    val title: String,
    val url: String = "",
    val onClick: () -> Unit = {}
)

val SignOutList = listOf(
    "쓰지 않는 앱이에요",
    "모임 목적과 맞지 않은 사용자가 많아요",
    "오류가 생겨서 쓸 수 없어요",
    "개인정보가 불안해요",
    "앱 사용법을 모르겠어요",
    "기타"
)

