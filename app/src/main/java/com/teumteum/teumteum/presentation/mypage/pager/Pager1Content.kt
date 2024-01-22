package com.teumteum.teumteum.presentation.mypage.pager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MeetingDummy1
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel

@Composable
fun MyPagePager1Content(
    viewModel: SettingViewModel
) {
    val userOpen by viewModel.userOpenMeetingList.collectAsState()
    val userHostOpen by viewModel.userHostMeetingList.collectAsState()

    val userClosed by viewModel.userClosedMeetingList.collectAsState()
    val userHostClosed by viewModel.userHostClosedMeetingList.collectAsState()
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
        // open이 모두 비어있을때
        if(userOpen.isEmpty() && userHostOpen.isEmpty()) {
            NoMoimItems()
        }
        else {
            userOpen.forEach { meeting ->
                MeetingItem(meeting)
            }
            userHostOpen.forEach { meeting ->  
                MyMoimItems(meeting = meeting)
            }
        }
        
        TmMarginVerticalSpacer(size = 27)

        Text(
            text = stringResource(id = R.string.setting_pager1_top2),
            style = TmTypo.current.HeadLine5,
            color = TmtmColorPalette.current.color_text_headline_primary
        )
        TmMarginVerticalSpacer(size = 20)
        if(userClosed.isEmpty() && userHostClosed.isEmpty()) {
            NoMoimItems()
        }
        else  {
            userClosed.forEach {
                MeetingItem(meeting = it)
            }
            userHostClosed.forEach { 
                MyMoimItems(meeting = it)
            }
        }
        
        TmMarginVerticalSpacer(size = 20)
    }
}