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
import androidx.navigation.NavController
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.mypage.MyPageFragmentDirections
import com.teumteum.teumteum.presentation.mypage.recommend.fragment.RecommendDetailFragmentDirections
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel

@Composable
fun MyPagePager1Content(
    viewModel: SettingViewModel,
    navController: NavController
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
            NoMoimItems(true, navController)

        }
        else {
            userOpen.forEach { meeting ->
                MeetingItem(meeting) { id->
                    val action = MyPageFragmentDirections.actionFragmentMyPageToFragmentMoim(id)
                    navController.navigate(action)
                }
            }
            userHostOpen.forEach { meeting ->  
                MyMoimItems(meeting) {id->
                    val action = MyPageFragmentDirections.actionFragmentMyPageToFragmentMoim(id)
                    navController.navigate(action)
                }
            }
        }
        TmMarginVerticalSpacer(size = 9)

        Text(
            text = stringResource(id = R.string.setting_pager1_top2),
            style = TmTypo.current.HeadLine5,
            color = TmtmColorPalette.current.color_text_headline_primary
        )
        TmMarginVerticalSpacer(size = 20)
        if(userClosed.isEmpty() && userHostClosed.isEmpty()) {
            NoMoimItems(false, navController)
        }
        else  {
            userClosed.forEach { meeting ->
                MeetingItem(meeting) { id->
                    val action = MyPageFragmentDirections.actionFragmentMyPageToFragmentMoim(id)
                    navController.navigate(action)
                }
            }
            userHostClosed.forEach { 
                MyMoimItems(meeting = it) {

                }
            }
        }
        
        TmMarginVerticalSpacer(size = 20)
    }
}