package com.teumteum.teumteum.presentation.mypage.pager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.teumteum.presentation.mypage.MyPageFragmentDirections
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel

@Composable
fun MyPagePager3Content(
    viewModel: SettingViewModel,
    navController: NavController
) {
    val userBookmark by viewModel.userBookmarkList.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        TmMarginVerticalSpacer(size = 20)
        if(userBookmark.isEmpty()) {

        }else {
            userBookmark.forEach { meeting ->
                BookmarkItem(meeting = meeting) { id->
                    val action = MyPageFragmentDirections.actionFragmentMyPageToFragmentMoim(id)
                    navController.navigate(action)
                }
            }
        }
    }

}