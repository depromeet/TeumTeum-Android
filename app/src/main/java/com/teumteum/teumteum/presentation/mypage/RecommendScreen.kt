package com.teumteum.teumteum.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.util.custom.view.model.FrontCard


@Composable
fun RecommendScreen() {
    TmScaffold(
        topbarText = "디프만님을 추천한 친구"
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TmMarginVerticalSpacer(size = 60)


        }
    }
}

@Composable
fun RecommendItem(recommend: Recommend) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end= 20.dp, bottom = 12.dp)
        .background(color = TmtmColorPalette.current.elevation_color_elevation_level01, shape = RoundedCornerShape(4.dp))
        .wrapContentHeight(),
        ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal =16.dp, vertical = 16.dp)
            .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

        }
    }
}