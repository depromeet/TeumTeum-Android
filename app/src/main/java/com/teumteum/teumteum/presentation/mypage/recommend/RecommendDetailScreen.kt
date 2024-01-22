package com.teumteum.teumteum.presentation.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import com.teumteum.teumteum.R
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.teumteum.teumteum.presentation.mypage.pager.MeetingItem
import com.teumteum.teumteum.presentation.mypage.pager.MyMoimItems
import com.teumteum.teumteum.presentation.mypage.pager.MyPagePager1Content
import com.teumteum.teumteum.presentation.mypage.pager.MyPagePager2Content
import com.teumteum.teumteum.presentation.mypage.pager.NoMoimItems
import com.teumteum.teumteum.presentation.mypage.recommend.RecommendDetailViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.UserInfoUiState
import com.teumteum.teumteum.util.custom.view.FrontCardView
import com.teumteum.teumteum.util.custom.view.model.FrontCard

@Composable
fun RecommendDetailScreen(
    navController: NavController,
    viewModel: RecommendDetailViewModel,
) {
    val userInfo by viewModel.friendInfo.collectAsState()

    TmScaffold(
        isSetting = false,
        onClick = {
            navController.popBackStack()
        },
        topbarText = "${userInfo?.name}님의 소개서"
    ) {
        val list = listOf("참여 모임", "받은 리뷰")
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
                    MyFriendFrontCard(viewModel)
                }
                TmMarginVerticalSpacer(size = 22)
                FriendBtn(text = "추천한 친구 ${userInfo?.friends}명", viewModel = viewModel)
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
                "내 모임" -> item {
                    FriendPager1Content(viewModel, navController) }
                "받은 리뷰" -> item { MyPagePager2Content() }
//                "북마크" -> item { MyPagePager3Content() }
            }
        }
    }
}

@Composable
fun MyFriendFrontCard(viewModel: RecommendDetailViewModel) {
    val frontCard = viewModel.getFrontCardFromInfo()
    FrontCardView(frontCard = frontCard)
}


@Composable
fun FriendBtn(text: String, viewModel: RecommendDetailViewModel) {
    var isFriend by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier
            .width(280.dp) ,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .height(46.dp),
            onClick = {
                !isFriend
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFriend) TmtmColorPalette.current.color_button_active
                else TmtmColorPalette.current.color_button_alternative
            ),
            shape = RoundedCornerShape(size = 4.dp)
        ) {
            Text(
                text = if (isFriend) "추천함" else "추천하기",
                style = TmTypo.current.HeadLine6,
                color = TmtmColorPalette.current.color_text_button_primary_default
            )
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .height(46.dp),
            onClick = {
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
}

@Composable
fun FriendCardView(frontCard: FrontCard) {
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

@Composable
fun FriendPager1Content(
    viewModel: RecommendDetailViewModel,
    navController: NavController
) {
    val userMeetingOpen by viewModel.userMeetingOpen.collectAsState()
    val userMeetingClosed by viewModel.userMeetingClosed.collectAsState()
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
        if(userMeetingOpen.isEmpty()) {
            NoMoimItems(false, navController)
        }
        else {
            userMeetingOpen.forEach {
                MeetingItem(it)
            }
        }
        TmMarginVerticalSpacer(size = 9)

        Text(
            text = stringResource(id = R.string.setting_pager1_top2),
            style = TmTypo.current.HeadLine5,
            color = TmtmColorPalette.current.color_text_headline_primary
        )
        TmMarginVerticalSpacer(size = 20)
        if(userMeetingClosed.isEmpty()) {
            NoMoimItems(false, navController)
        }
        else  {
            userMeetingClosed.forEach {
                MeetingItem(meeting = it)
            }
        }

        TmMarginVerticalSpacer(size = 20)
    }
}