package com.teumteum.base.component.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.teumteum.base.component.compose.theme.TmtmColorPalette

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TmIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = TmtmColorPalette.current.color_button_active,
    inactiveColor: Color = TmtmColorPalette.current.color_icon_level02_disabled,
    indicatorHeight: Dp = 4.dp,
) {
    val pageCount = pagerState.pageCount
    val currentPage = pagerState.currentPage
    val progress = (pagerState.currentPageOffset + currentPage) / (pageCount - 1).coerceAtLeast(1)

    Box(modifier = modifier.height(indicatorHeight).width(80.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // 전체 bar
            drawLine(
                color = inactiveColor,
                start = Offset(x = 0f, y = height / 2),
                end = Offset(x = width, y = height / 2),
                strokeWidth = height
            )

            // active bar
            drawLine(
                color = activeColor,
                start = Offset(x = 0f, y = height / 2),
                end = Offset(x = width * progress, y = height / 2),
                strokeWidth = height
            )
        }
    }
}
