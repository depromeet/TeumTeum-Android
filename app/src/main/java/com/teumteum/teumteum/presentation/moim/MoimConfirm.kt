package com.teumteum.teumteum.presentation.moim

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import com.google.accompanist.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.teumteum.base.component.compose.TmIndicator
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R


@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun MoimConfirm() {
    TmScaffold(
        topbarText = stringResource(id = R.string.moim_confirm_appbar),
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background)
                .verticalScroll(scrollState)
        ) {
            MoimPhotoPager(size = 4)
            MoimConfirmInfo()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MoimPhotoPager(size: Int) {
    val pagerState = rememberPagerState()
    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(276.dp),
            count = size,
            state = pagerState
        ) {page->
            Text(
                modifier = Modifier.wrapContentSize(),
                text = page.toString(),
                textAlign = TextAlign.Center,
                style = TmTypo.current.HeadLine2
            )
//        val uri = imageUris[page]
//        Image(
//            painter = rememberAsyncImagePainter(uri),
//            contentDescription = null,
//        )
        }

        if (size > 1) {
            TmIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                activeColor = TmtmColorPalette.current.color_button_active,
                inactiveColor = TmtmColorPalette.current.color_icon_level02_disabled,
                indicatorHeight = 4.dp
            )
        }
    }
}

@Composable
fun MoimConfirmInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "제목제목제목제목ㅁ제목제목제목제목제목제제목제목목",
                style = TmTypo.current.HeadLine3,
                modifier = Modifier
                    .width(320.dp)
                    .align(Alignment.Top),
                color = TmtmColorPalette.current.color_text_headline_primary
            )
            TmMarginHorizontalSpacer(size = 8)
            Image(
                painterResource(id = R.drawable.icon_share),
                contentDescription = null,
                modifier= Modifier
                    .size(24.dp)
                    .offset(y = 7.dp)
            )
        }
        TmMarginVerticalSpacer(size = 16)
    }
}

@Composable
fun MoimInfoCard() {
    Box(
        modifier = Modifier.fillMaxWidth().height(140.dp).background(
            color = TmtmColorPalette.current.elevation_color_elevation_level01,
            shape = RoundedCornerShape(8.dp)
        )
    ) {}
}


