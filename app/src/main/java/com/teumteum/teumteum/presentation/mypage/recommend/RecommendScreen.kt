package com.teumteum.teumteum.presentation.mypage.recommend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.presentation.mypage.Recommend
import com.teumteum.teumteum.presentation.mypage.RecommendDummy
import com.teumteum.teumteum.presentation.mypage.setting.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.SettingViewModel


@Composable
fun RecommendScreen(viewModel: SettingViewModel) {
    TmScaffold(
        topbarText = "정은아님을 추천한 친구",
        onClick = { viewModel.updateSettingStatus(SettingStatus.DEFAULT) }
    ) {
        Spacer(modifier = Modifier.height(68.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =  Arrangement.Top
            ) {
                item {
                    Spacer(modifier = Modifier.height(68.dp))
                }
                items(RecommendDummy) { recommend ->
                    RecommendItem(recommend = recommend)
                }
            }
    }
}

@Composable
fun RecommendItem(recommend: Recommend) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
        .background(
            color = TmtmColorPalette.current.elevation_color_elevation_level01,
            shape = RoundedCornerShape(4.dp)
        )
        .wrapContentHeight(),
        ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RecommendRow(recommend = recommend)
            Text(
                text = "${recommend.jobName}",
                style = TmTypo.current.Body2,
                color= TmtmColorPalette.current.color_text_body_teritary,
            )

        }
    }
}
@Composable
fun RecommendRow(recommend: Recommend) {
    Row(modifier = Modifier
        .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start) {
        Image(
            painter = painterResource(id = recommend.image),
            contentDescription =null,
            modifier = Modifier
                .size(32.dp)
                .padding(vertical = 0.dp)
        )
        TmMarginHorizontalSpacer(size = 8)
        Text(
            text = "${recommend.name}",
            style = TmTypo.current.HeadLine3,
            color= TmtmColorPalette.current.color_text_headline_primary,
        )
    }
}
