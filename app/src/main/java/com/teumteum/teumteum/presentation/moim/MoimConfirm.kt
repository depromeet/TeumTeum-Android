package com.teumteum.teumteum.presentation.moim

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MoimPhotoPager(size = 4)



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
//            HorizontalPagerIndicator(
//                pagerState = pagerState,
//                modifier = Modifier
//                    .width(80.dp)
//                    .height(4.dp)
//                    .align(Alignment.BottomCenter),
//                spacing = 0.dp,
//                inactiveColor = TmtmColorPalette.current.color_icon_level02_disabled,
//                activeColor = TmtmColorPalette.current.color_button_active
//            )

            TmIndicator(
                pagerState = pagerState,
                modifier = Modifier.align(Alignment.BottomCenter),
                activeColor = TmtmColorPalette.current.color_button_active,
                inactiveColor = TmtmColorPalette.current.color_icon_level02_disabled,
                indicatorHeight = 4.dp
            )
        }
    }
}

