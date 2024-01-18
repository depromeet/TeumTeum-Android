package com.teumteum.teumteum.presentation.mypage.setting


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.teumteum.teumteum.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette


@Composable
fun SettingToggle(title:String, viewModel:SettingViewModel) {
    val isToggle by viewModel.alarmState.collectAsState()
    val toggleImage : Int = if (isToggle) R.drawable.ic_toggle_active else R.drawable.ic_toggle
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = TmtmColorPalette.current.color_background)
        .height(64.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = title, style = TmTypo.current.HeadLine7, color= TmtmColorPalette.current.color_text_headline_primary)
            Image(painter = painterResource(id = toggleImage), contentDescription = null,
                modifier = Modifier.clickable { viewModel.onToggleChange(!isToggle) })
        }
    }
}