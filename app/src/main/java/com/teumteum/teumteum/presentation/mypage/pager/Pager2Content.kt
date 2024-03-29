package com.teumteum.teumteum.presentation.mypage.pager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.UserGrade
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.emptyUserGrade

@Composable
fun MyPagePager2Content(
    viewModel: MyPageViewModel
) {
    val reviews by viewModel.reviews.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement =  Arrangement.Top
    ) {
        TmMarginVerticalSpacer(size = 20)
        if (reviews.isNotEmpty()) {
            reviews.forEach {
                MyPager2Item(userGrade = it)
            }
        } else {
            emptyUserGrade.forEach { 
                MyPager2Item(userGrade = it)
            }

        }
        TmMarginVerticalSpacer(size = 20)
    }
}

@Composable
fun MyPager2Item(userGrade: UserGrade) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        MyPagerPager2Row(userGrade = userGrade)
        Text(
            text = "${userGrade.count}",
            style = TmTypo.current.HeadLine4,
            color= TmtmColorPalette.current.color_text_headline_teritary
        )
    }
}

@Composable
fun MyPagerPager2Row(userGrade: UserGrade) {
    Row(modifier = Modifier
        .wrapContentWidth()
        .wrapContentHeight(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = userGrade.image),
            contentDescription = "Grade Image"
        )
        TmMarginHorizontalSpacer(size = 8)
        Box(modifier = Modifier
            .wrapContentSize()
            .background(
                color = TmtmColorPalette.current.elevation_color_elevation_level01,
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 12.dp,
                    bottomEnd = 12.dp,
                    bottomStart = 12.dp
                )
            )
        ) {
            Text(
                text = "${userGrade.text}",
                style = TmTypo.current.HeadLine6,
                color= TmtmColorPalette.current.color_text_headline_secondary,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }
    }
}
