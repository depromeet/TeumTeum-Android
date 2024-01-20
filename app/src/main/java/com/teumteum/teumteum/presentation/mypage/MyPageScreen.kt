package com.teumteum.teumteum.presentation.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.TmTabItem
import com.teumteum.base.component.compose.TmTabRow
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.presentation.moim.ScreenState
import com.teumteum.teumteum.presentation.mypage.pager.MyPagePager1Content
import com.teumteum.teumteum.presentation.mypage.pager.MyPagePager2Content
import com.teumteum.teumteum.presentation.mypage.pager.MyPagePager3Content
import com.teumteum.teumteum.presentation.mypage.setting.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.SettingViewModel
import com.teumteum.teumteum.util.custom.view.FrontCardView
import com.teumteum.teumteum.util.custom.view.model.FrontCard
@Preview
@Composable
fun MyPageScreen(
    viewModel: SettingViewModel = SettingViewModel(),
) {
    TmScaffold(
        isSetting = true,
        onClick = { viewModel.updateSettingStatus(SettingStatus.SETTING) },
        topbarText = "정은아님의 소개서"
    ) {
        val scrollState = rememberScrollState()
        val frontCardData = FrontCard()
        val list = listOf("내 모임", "받은 리뷰", "북마크")
        val selectedTab = remember { mutableStateOf(list[0]) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TmMarginVerticalSpacer(size = 78)
            FrontCardView(frontCard =  frontCardData)
            TmMarginVerticalSpacer(size = 22)
            SettingBtn(viewModel)
            TmMarginVerticalSpacer(size = 10)
            TmTabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                tabItemList =list,
                tabContent = { item ->
                    TmTabItem(
                        text = item,
                        isSelected = item == selectedTab.value,
                        onSelect = { selectedTab.value = item}
                    )
                }
            )
            when(selectedTab.value) {
                "내 모임" -> MyPagePager1Content()
                "받은 리뷰" -> MyPagePager2Content()
                "북마크" -> MyPagePager3Content()
            }
        }
    }
}


@Composable
fun SettingBtn(viewModel: SettingViewModel) {
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .padding(horizontal = 40.dp, vertical = 10.dp),
        onClick = { viewModel.updateSettingStatus(SettingStatus.RECOMMEND)},
        colors = ButtonDefaults.buttonColors(containerColor = TmtmColorPalette.current.color_button_active),
        shape = RoundedCornerShape(size = 4.dp)
    ) {
        Text(
            text = "추천한 친구 16명",
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
            .fillMaxWidth()
            .height(400.dp)
            .padding(horizontal = 40.dp)
    )
}