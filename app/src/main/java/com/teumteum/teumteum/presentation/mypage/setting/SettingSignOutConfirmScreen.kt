package com.teumteum.teumteum.presentation.mypage.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teumteum.base.component.compose.TeumDivider
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.UserInfoUiState

@Composable
fun SettingSignOutConfirm(viewModel: SettingViewModel, myPageViewModel: MyPageViewModel, navController: NavController) {
    val userInfoState by myPageViewModel.userInfoState.collectAsState()
    val name = when (userInfoState) {
        is UserInfoUiState.Success -> "${(userInfoState as UserInfoUiState.Success).data.name}님"
        else -> "로딩 중..."
    }
    TmScaffold(
        onClick = { navController.popBackStack() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background)
        ) {
            TmMarginVerticalSpacer(size = 68)
            Text(
                text = name,
                style = TmTypo.current.HeadLine3,
                color= TmtmColorPalette.current.color_text_headline_primary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Text(
                text = stringResource(id = R.string.setting_signout_title1),
                style = TmTypo.current.HeadLine3,
                color= TmtmColorPalette.current.color_text_headline_primary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            TmMarginVerticalSpacer(size = 8)
            Text(
                text = stringResource(id = R.string.setting_signout_text1),
                style = TmTypo.current.Body1,
                color= TmtmColorPalette.current.color_text_body_quaternary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            TmMarginVerticalSpacer(size = 80)
            SignOutImageColumn()
            Spacer(modifier = Modifier.weight(1f))
            SignOutConfirmCheck(viewModel)
            TeumDivider()
            SettingSignOutBtn2(text = stringResource(id = R.string.setting_signout_btn2), viewModel)
            TmMarginVerticalSpacer(size = 24)
        }

    }
}

@Composable
fun SettingSignOutBtn2(
    text: String,
    viewModel: SettingViewModel,
) {
    val selectedReasonsCount by viewModel.signoutReason.collectAsState()
    val isCheckboxChecked by viewModel.isCheckboxChecked.collectAsState()
    val isEnabled = selectedReasonsCount.isNotEmpty() && isCheckboxChecked
    val buttonColors = if (isEnabled) TmtmColorPalette.current.color_button_active else TmtmColorPalette.current.color_button_disabled
    val textColors = if(isEnabled) TmtmColorPalette.current.Gray900 else TmtmColorPalette.current.color_text_button_primary_disabled
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        enabled = isEnabled,
        onClick = { viewModel.signout() },
        colors = ButtonDefaults.buttonColors(containerColor = buttonColors),
        shape = RoundedCornerShape(size = 4.dp)
    ) {
        Text(
            text = text,
            style = TmTypo.current.HeadLine6,
            color = textColors
        )
    }
}

@Composable
fun SignOutImageColumn() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.ic_signout_img),
            contentDescription = null)
    }
}

@Composable
fun SignOutConfirmCheck(viewModel:SettingViewModel) {
    val isCheckboxChecked by viewModel.isCheckboxChecked.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { viewModel.toggleCheckbox() }
            .padding(start = 20.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = if (isCheckboxChecked) R.drawable.ic_check_box_select else R.drawable.ic_check_box_default),
            contentDescription = null
        )
        TmMarginHorizontalSpacer(size = 8)
        Text(
            text = stringResource(id = R.string.setting_signout_text2),
            style = TmTypo.current.Body1,
            color= TmtmColorPalette.current.color_text_body_teritary,
        )

    }
}