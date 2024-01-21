package com.teumteum.teumteum.presentation.mypage.editCard

import androidx.compose.runtime.Composable
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmInputField
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel

@Composable
fun EditCardScreen(
    MyPageViewModel: MyPageViewModel,
    viewModel: SettingViewModel,
    navController: NavController
) {
    val activity = LocalContext.current as? MainActivity
    TmScaffold(
        topbarText = stringResource(id = R.string.setting_edit_card_topbar),
        onClick = {
            navController.popBackStack()
            activity?.showBottomNavi()
        }
    ){

        val scrollState = rememberScrollState()
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
            .background(color = TmtmColorPalette.current.color_background)
            .wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {
            TmMarginVerticalSpacer(size = 80)

            //이름
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label1))
            TmMarginVerticalSpacer(size = 8)
            TmInputField(text = R.string.setting_edit_card_placeholder1, text_error = R.string.setting_edit_card_error1)

            //생년월일
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label2))
            TmMarginVerticalSpacer(size = 8)
            TmInputField(text = R.string.setting_edit_card_placeholder2, text_error = R.string.setting_edit_card_error2)

            //상태
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label3))
            TmMarginVerticalSpacer(size = 8)
            EditCardBottomBox(text = R.string.setting_edit_card_placeholder3)

            //직장명
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label4))
            TmMarginVerticalSpacer(size = 8)
            TmInputField(text = R.string.setting_edit_card_placeholder4, text_error = R.string.setting_edit_card_error4)

            //직군
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label5))
            TmMarginVerticalSpacer(size = 8)
            EditCardBottomBox(text = R.string.setting_edit_card_placeholder5)

            //직무
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label6))
            TmMarginVerticalSpacer(size = 8)
            EditCardBottomBox(text = R.string.setting_edit_card_placeholder6)

            //MBTI
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label7))
            TmMarginVerticalSpacer(size = 8)
            EditCardBottomBox(text = R.string.setting_edit_card_placeholder7)

            //관심 지역
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label8))
            TmMarginVerticalSpacer(size = 8)
            EditCardBottomBox(text = R.string.setting_edit_card_placeholder8)

            //자기 개발
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label9))
            TmMarginVerticalSpacer(size = 8)

            //틈틈 목표
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label10))
            TmMarginVerticalSpacer(size = 8)
            TmInputField(text = R.string.setting_edit_card_placeholder10,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(134.dp)
            )
        }
    }
}

@Composable
fun EditCardBottomBox(@StringRes text: Int ) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(
            color = TmtmColorPalette.current.elevation_color_elevation_level01,
            shape = RoundedCornerShape(4.dp)
        )
        .clickable {}
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.material3.Text(
                text = stringResource(id = text),
                color = TmtmColorPalette.current.color_text_body_quinary,
                style = TmTypo.current.Body1,
            )
            Image(painter = painterResource(id = R.drawable.ic_arrow_down_l),
                contentDescription = null
            )
        }
    }
    TmMarginVerticalSpacer(size = 20)
}

@Composable
fun EditCardLabel(string: String) {
    androidx.compose.material3.Text(
        text = string,
        style = TmTypo.current.Body2,
        color = TmtmColorPalette.current.color_text_body_quaternary,
        modifier = Modifier.clickable {  }
    )
}