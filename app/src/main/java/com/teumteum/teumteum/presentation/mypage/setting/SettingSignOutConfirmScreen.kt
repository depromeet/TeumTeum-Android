package com.teumteum.teumteum.presentation.mypage.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TeumDivider
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R

@Preview
@Composable
fun SettingSignOutConfirm() {
    TmScaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background)
        ) {
            TmMarginVerticalSpacer(size = 68)
            Text(
                text = "정은아님",
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
            SignOutConfirmCheck()
            TeumDivider()
            SettingSignOutBtn1(text = stringResource(id = R.string.setting_signout_btn2))
            TmMarginVerticalSpacer(size = 24)
        }

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
fun SignOutConfirmCheck() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 20.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = R.drawable.ic_check_box_default), contentDescription = null)
        TmMarginHorizontalSpacer(size = 8)
        Text(
            text = stringResource(id = R.string.setting_signout_text2),
            style = TmTypo.current.Body1,
            color= TmtmColorPalette.current.color_text_body_teritary,
        )

    }
}