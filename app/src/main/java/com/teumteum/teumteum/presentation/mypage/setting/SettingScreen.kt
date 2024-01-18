package com.teumteum.teumteum.presentation.mypage.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.teumteum.R
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.presentation.mypage.getMemberSetting

@Preview
@Composable
fun SettingScreen() {
    val viewModel = SettingViewModel()
    TmScaffold(
        topbarText = stringResource(id = R.string.setting_service_guide_topbar)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.elevation_color_elevation_level01)
        ) {
            TmMarginVerticalSpacer(size = 60)
            SettingAccountRow()
            TmMarginVerticalSpacer(size = 8)
            SettingToggle(title = "푸시알림", viewModel = viewModel)
            SettingColumn2()
            TmMarginVerticalSpacer(size = 14)
            Text(
                text = stringResource(id = R.string.setting_version_text),
                style = TmTypo.current.Body2,
                color= TmtmColorPalette.current.color_text_body_quinary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

    }
}

@Composable
fun SettingAccountRow() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(color = TmtmColorPalette.current.color_background)
        .padding(horizontal = 20.dp)
        .height(70.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "정은아", style = TmTypo.current.HeadLine6, color= TmtmColorPalette.current.color_text_headline_primary)
            TmMarginHorizontalSpacer(size = 4)
            Text(text = stringResource(id = R.string.setting_my_info_edit_text), style = TmTypo.current.Body3, color= TmtmColorPalette.current.color_text_body_teritary)
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right_l ),
            contentDescription = "right_arrow", tint= Color.Unspecified,
            modifier = Modifier.size(20.dp)
        )

    }
}

@Composable
fun SettingColumn2() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = TmtmColorPalette.current.elevation_color_elevation_level01),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        items(getMemberSetting()) { item->
            SettingTitle(
                title = item.title,
                onClick = {
                    item.onClick()
                }
            )
        }
    }
}

@Composable
fun SettingTitle(title: String, onClick: ()-> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .background(color = TmtmColorPalette.current.color_background)
        .height(48.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = title, style = TmTypo.current.HeadLine7, color= TmtmColorPalette.current.color_text_body_primary)
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right_l ),
                contentDescription = "right_arrow", tint= Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}