package com.teumteum.teumteum.presentation.mypage

import android.graphics.drawable.GradientDrawable
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
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
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.mypage.pager.MyPagePager1Content
import com.teumteum.teumteum.presentation.mypage.pager.MyPagePager2Content
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.UserInfoUiState
import com.teumteum.teumteum.util.custom.view.BackCardView
import com.teumteum.teumteum.util.custom.view.FrontCardView
import com.teumteum.teumteum.util.custom.view.model.BackCard
import com.teumteum.teumteum.util.custom.view.model.FrontCard
import com.teumteum.teumteum.util.custom.view.model.Interest


enum class CardFace {
    Front, Back
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyPageScreen(
    navController: NavController,
    viewModel: SettingViewModel,
    myPageViewModel: MyPageViewModel,
) {
    val userInfoState by myPageViewModel.userInfoState.collectAsState()
    val frontCardState by myPageViewModel.frontCardState.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val backCard by myPageViewModel.backCardState.collectAsState()

    val density = LocalDensity.current.density
    val state = rememberSwipeableState(initialValue = CardFace.Front)
    val anchors = mapOf(0f to CardFace.Front, with(LocalDensity.current) { -300.dp.toPx() } to CardFace.Back)
    val rotationYAnim = remember { Animatable(0f) }

    val friends = when (userInfoState) {
        is UserInfoUiState.Success -> "추천한 친구 ${(userInfoState as UserInfoUiState.Success).data.friends}명"
        else -> "로딩 중..."
    }

    LaunchedEffect(state.targetValue) {
        rotationYAnim.animateTo(
            targetValue = if (state.targetValue == CardFace.Back) 180f else 0f,
            animationSpec = tween(durationMillis = 500)
        )
    }

    TmScaffold(
        isSetting = true,
        onClick = {
            navController.navigate(R.id.fragment_setting)
        },
        topbarText = "${userName}님의 소개서"
    ) {
        val list = listOf("내 모임", "받은 리뷰", "북마크")
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
                Box(
                    modifier = Modifier
                        .swipeable(
                            state = state,
                            anchors = anchors,
                            orientation = Orientation.Horizontal,
                            thresholds = { _, _ -> FractionalThreshold(0.3f) }
                        )
                        .graphicsLayer {
                            rotationY = rotationYAnim.value
                            cameraDistance = 12f * density
                        }
                ) {
                    if (state.progress.to == CardFace.Front) {
                        // 앞면 카드 컨텐츠 표시
                        MyPageFrontCard(frontCard = frontCardState)
                    } else {
                        MyPageBackCard(backCard = backCard, viewModel = myPageViewModel)
                        Image(
                            painter = painterResource(id = R.drawable.ic_floating_edit),
                            contentDescription = "Character Image",
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .offset(x = (25).dp, y = (-30).dp)
                                .clickable { navController.navigate(R.id.fragment_edit_card) }
                        )
                    }
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
                "내 모임" -> item {
                    MyPagePager1Content(viewModel, navController)
                }

                "받은 리뷰" -> item { MyPagePager2Content() }
                "북마크" -> item {
                    MyPagePager3Content(viewModel, navController)
                }
            }
        }
    }
}

@Composable
fun MyPageFrontCard(frontCard: FrontCard) {
    FrontCardView(frontCard = frontCard)
}

@Composable
fun MyPageBackCard(backCard: BackCard, viewModel:MyPageViewModel) {
    BackCardView(backCard = backCard, viewModel)
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

@Composable
fun BackCardView(backCard: BackCard, viewModel:MyPageViewModel) {
    val interests = viewModel.interests.collectAsState().value.map { Interest(it) }
    AndroidView(
        factory = { context ->
            BackCardView(context).apply {
                getInstance(backCard)
                setIsModifyDetail(isModifyDetail = false)
                submitInterestList(interests)
                setIsModifyDetail(isModifyDetail = true)
                isModify = true
                rotationY = 180f
            }
        },
        update = { view ->
            val currentInterestsSet = view.currentList.value.orEmpty().toSet()
            val interestsSet = interests.toSet()
            if (currentInterestsSet != interestsSet) {
                view.submitInterestList(interests)
            }
        },
        modifier = Modifier
            .width(280.dp)
            .height(400.dp)
    )
}