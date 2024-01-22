package com.teumteum.teumteum.presentation.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import com.teumteum.teumteum.R
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.TmTabItem
import com.teumteum.base.component.compose.TmTabRow
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.pager.MyPagePager1Content
import com.teumteum.teumteum.presentation.mypage.pager.MyPagePager2Content
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.UserInfoUiState
import com.teumteum.teumteum.util.custom.view.FrontCardView
import com.teumteum.teumteum.util.custom.view.model.FrontCard

@Composable
fun MyPageScreen(
    navController: NavController,
    viewModel: SettingViewModel,
    myPageViewModel: MyPageViewModel
) {
    val activity = LocalContext.current as? MainActivity
    val userInfoState by myPageViewModel.userInfoState.collectAsState()
    val frontCardState by myPageViewModel.frontCardState.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val friends = when (userInfoState) {
        is UserInfoUiState.Success -> "추천한 친구 ${(userInfoState as UserInfoUiState.Success).data.friends}명"
        else -> "로딩 중..."
    }

    TmScaffold(
        isSetting = true,
        onClick = {
            navController.navigate(R.id.fragment_setting)
                  },
        topbarText = "${userName}님의 소개서"
    ) {
        val list = listOf("내 모임", "받은 리뷰")
        val selectedTab = remember { mutableStateOf(list[0]) }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                TmMarginVerticalSpacer(size = 78)
                Box {
                    MyPageFrontCard(frontCard = frontCardState)
                    Image(
                        painter = painterResource(id = R.drawable.ic_floating_edit),
                        contentDescription = "Character Image",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = (-24).dp, y = (-22).dp)
                            .clickable { navController.navigate(R.id.fragment_edit_card) }
                    )
                }
                TmMarginVerticalSpacer(size = 22)
                SettingBtn(friends, navController, myPageViewModel)
                TmMarginVerticalSpacer(size = 10)
            }

            item {
                TmTabRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    tabItemList = list,
                    tabContent = { item ->
                        TmTabItem(
                            text = item,
                            isSelected = item == selectedTab.value,
                            onSelect = { selectedTab.value = item }
                        )
                    }
                )
            }
            when (selectedTab.value) {
                "내 모임" -> item { MyPagePager1Content() }
                "받은 리뷰" -> item { MyPagePager2Content() }
//                "북마크" -> item { MyPagePager3Content() }
            }
        }
    }
}

@Composable
fun MyPageFrontCard(frontCard: FrontCard) {
    FrontCardView(frontCard = frontCard)
}


@Composable
fun SettingBtn(text: String, navController: NavController, viewModel: MyPageViewModel) {
    androidx.compose.material3.Button(
        modifier = Modifier
            .width(280.dp)
            .height(76.dp)
            .padding(vertical = 10.dp),
        onClick = {
            viewModel.loadFriends()
            navController.navigate(R.id.fragment_recommend)
        },
        colors = ButtonDefaults.buttonColors(containerColor = TmtmColorPalette.current.color_button_active),
        shape = RoundedCornerShape(size = 4.dp)
    ) {
        Text(
            text = text,
            style = TmTypo.current.HeadLine6,
            color = TmtmColorPalette.current.color_text_button_primary_default
        )
    }
}

@Composable
fun FrontCardView(frontCard: FrontCard) {
    AndroidView(
        factory = { context ->
            FrontCardView(context).apply {
                getInstance(frontCard)
            }
        },
        update = { view ->
            view.getInstance(frontCard)
        },
        modifier = Modifier
            .width(280.dp)
            .height(400.dp)
    )
}