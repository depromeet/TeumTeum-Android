package com.teumteum.teumteum.presentation.mypage.pager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.mypage.MeetingDummy
import com.teumteum.teumteum.presentation.mypage.MeetingDummy1

@Composable
fun MyPagePager1Content() {
    val moim1: Int = 1;
    val moim2: Int = 0;
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        TmMarginVerticalSpacer(size = 20)
        Text(
            text = stringResource(id = R.string.setting_pager1_top1),
            style = TmTypo.current.HeadLine5,
            color = TmtmColorPalette.current.color_text_headline_primary
        )
        TmMarginVerticalSpacer(size = 20)
        if(moim1 == 0) {
            NoMoimItems()
        } else {
            MyMoimItems(MeetingDummy1)
        }
        TmMarginVerticalSpacer(size = 27)

        Text(
            text = stringResource(id = R.string.setting_pager1_top2),
            style = TmTypo.current.HeadLine5,
            color = TmtmColorPalette.current.color_text_headline_primary
        )
        TmMarginVerticalSpacer(size = 20)
        if (moim2 == 0) {
            NoMoimItems(false)
        } else {
            MyMoimBadge()
        }
        TmMarginVerticalSpacer(size = 20)
    }
}

@Composable
fun MeetingColumn() {

}
