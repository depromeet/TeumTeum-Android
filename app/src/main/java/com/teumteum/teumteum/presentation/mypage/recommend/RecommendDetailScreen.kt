package com.teumteum.teumteum.presentation.mypage.recommend

import android.app.Activity
import androidx.compose.foundation.background
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.TmTabItem
import com.teumteum.base.component.compose.TmTabRow
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.presentation.mypage.FrontCardView
import com.teumteum.teumteum.presentation.mypage.pager.MeetingItem
import com.teumteum.teumteum.presentation.mypage.pager.MyPagePager2Content
import com.teumteum.teumteum.presentation.mypage.pager.NoMoimItems
import com.teumteum.teumteum.presentation.mypage.recommend.fragment.RecommendDetailFragmentDirections
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.RecommendDetailViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.UserInfoUiState
import timber.log.Timber

@Composable
fun RecommendDetailScreen(
    navController: NavController,
    viewModel: RecommendDetailViewModel,
    userId: Int,
    isJoinView: Boolean,
    activity: Activity
) {
    val friendsCount by viewModel.friendsList.collectAsState()
    val userInfoState by viewModel.userInfoState.collectAsState()

    val topbarText = when (val state = userInfoState) {
        is UserInfoUiState.Success -> "${state.data.name}의 소개서"
        is UserInfoUiState.Loading -> "로딩 중..."
        else -> "" // 또는 다른 기본 텍스트
    }

    TmScaffold(
        isSetting = false,
        onClick = {
            if (isJoinView) {
                activity.finish()
                (activity as? BindingActivity<*>)?.closeActivitySlideAnimation()
            } else navController.popBackStack()
        },
        topbarText = topbarText
    ) {
        val list = listOf("참여 모임", "받은 리뷰")

        val selectedTab = remember { mutableStateOf(list[0]) }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                TmMarginVerticalSpacer(size = 78)
                Box {
                    MyFriendFrontCard(viewModel)
                }
                TmMarginVerticalSpacer(size = 22)
                FriendBtn(
                    text = "추천한 친구 ${friendsCount.size}명",
                    viewModel = viewModel,
                    userId= userId,
                    navController = navController
                )
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
                    "참여 모임" -> item {
                        FriendPager1Content(viewModel, navController) }
                    "받은 리뷰" -> item { MyPagePager2Content() }
//                "북마크" -> item { MyPagePager3Content() }
            }

        }
    }
    BackHandler {
        // Handle the back button press
        activity.finish()
        (activity as? BindingActivity<*>)?.closeActivitySlideAnimation()
    }
}

@Composable
fun MyFriendFrontCard(viewModel: RecommendDetailViewModel) {
    val frontCard = viewModel.getFrontCardFromInfo()
    FrontCardView(frontCard = frontCard)
}


@Composable
fun FriendBtn(text: String, viewModel: RecommendDetailViewModel, userId: Int, navController: NavController) {
    val isFriend by viewModel.isFriend.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .height(46.dp),
            onClick = {
                viewModel.postFriend(userId.toLong())
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFriend) TmtmColorPalette.current.color_button_alternative else TmtmColorPalette.current.color_button_active
            ),
            shape = RoundedCornerShape(size = 4.dp)
        ) {
            Text(
                text = if (isFriend) "추천함" else "추천하기",
                style = TmTypo.current.HeadLine6,
                color = if(isFriend) TmtmColorPalette.current.color_text_button_alternative else TmtmColorPalette.current.color_text_button_primary_default
            )
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .height(46.dp),
            onClick = {
                val action = RecommendDetailFragmentDirections.actionFragmentRecommendDetailToFragmentRecommend(userId)
                navController.navigate(action)
            },
            colors = ButtonDefaults.buttonColors(containerColor = TmtmColorPalette.current.color_button_alternative),
            shape = RoundedCornerShape(size = 4.dp)
        ) {
            Text(
                text = text,
                style = TmTypo.current.HeadLine6,
                color = TmtmColorPalette.current.color_text_button_alternative
            )
        }
    }
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
            Timber.d("No Open Meetings")
            NoMoimItems(false, navController)
        }
        else {
            Timber.d("Open Meetings")
            userMeetingOpen.forEach {
                MeetingItem(it) {}
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
            Timber.d("No Closed Meetings")
            NoMoimItems(false, navController)
        }
        else  {
            Timber.d("Closed Meetings")
            userMeetingClosed.forEach {
                MeetingItem(meeting = it) {
//                        id->
//                    val action = RecommendDetailFragmentDirections.actionFragmentRecommendDetailToFragmentMoim(id)
//                    navController.navigate(action)
                }
            }
        }
        TmMarginVerticalSpacer(size = 20)
    }
}