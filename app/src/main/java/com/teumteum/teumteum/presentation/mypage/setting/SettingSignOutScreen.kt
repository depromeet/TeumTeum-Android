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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
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
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SignOutList
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel

@Composable
fun SettingSignOutScreen(
    viewModel: SettingViewModel,
    navController: NavController
) {
    TmScaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background)
        ) {
            TmMarginVerticalSpacer(size = 68)
            Text(
                text = stringResource(id = R.string.setting_signout_title),
                style = TmTypo.current.HeadLine3,
                color= TmtmColorPalette.current.color_text_headline_primary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            TmMarginVerticalSpacer(size = 64)
            SettingSignOutColumn(viewModel)
            Spacer(modifier = Modifier.weight(1f))
            TeumDivider()
            SettingSignOutBtn1(text = stringResource(id = R.string.setting_signout_btn1), viewModel, navController)
            TmMarginVerticalSpacer(size = 24)
        }
    }
}

@Composable
fun SettingSignOutBtn1(
    text: String,
    viewModel: SettingViewModel,
    navController: NavController
) {
    val selectedReasonsCount by viewModel.signoutReason.collectAsState()
    val isEnabled = selectedReasonsCount.isNotEmpty()
    val buttonColors = if (isEnabled) TmtmColorPalette.current.color_button_active else TmtmColorPalette.current.Gray200
    val textColors = if(isEnabled) TmtmColorPalette.current.GreyWhite else TmtmColorPalette.current.Gray300
        androidx.compose.material3.Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            enabled = isEnabled,
            onClick = { navController.navigate(R.id.fragment_signout_confirm) },
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
fun SettingSignOutColumn(viewModel: SettingViewModel) {
    val selectedItems by viewModel.signoutReason.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = TmtmColorPalette.current.color_background),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        items(SignOutList) { item ->
            val isSelected = item in selectedItems
            SettingSignOutItem(string = item, isSelected = isSelected) {
                if (isSelected) {
                    viewModel.removeItem(item)
                } else {
                    viewModel.addItem(item)
                }
            }
        }
    }
}

@Composable
fun SettingSignOutItem(string: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 9.dp)
            .wrapContentHeight()
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = string,
            style = TmTypo.current.HeadLine7,
            color= TmtmColorPalette.current.color_text_body_secondary,
        )
        Image(
            painter = if (isSelected) painterResource(id = R.drawable.ic_check_box_select)
            else painterResource(id = R.drawable.ic_check_box_default),
            contentDescription = null
        )
    }
}
