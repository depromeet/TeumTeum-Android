package com.teumteum.teumteum.presentation.mypage.setting


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette


@Composable
fun SettingToggle(title:String, viewModel:SettingViewModel) {
    val isToggle by viewModel.alarmState.collectAsState()
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
            ToggleBtn(selected = isToggle) {
                viewModel.onToggleChange(it)
            }
        }
    }
}

@Composable
fun ToggleBtn(
    selected: Boolean,
    modifier : Modifier = Modifier,
    onUpdate: (Boolean) -> Unit
) {
    Card(
        modifier = modifier
            .width(58.dp)
            .height(28.dp)
            .clickable { onUpdate(!selected) },
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (selected) TmtmColorPalette.current.color_button_active
                    else TmtmColorPalette.current.color_background,
                ), contentAlignment = if (selected) Alignment.CenterEnd else Alignment.CenterStart
        ) {
            CheckCircle(modifier = Modifier.padding(5.dp), selected = selected)
        }

    }
}

@Composable
fun CheckCircle(
    modifier: Modifier = Modifier,
    selected: Boolean,
) {
    Box(
        modifier = modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(
                if (selected) TmtmColorPalette.current.color_button_active
                else TmtmColorPalette.current.color_text_button_primary_default,
            )
    )
}
