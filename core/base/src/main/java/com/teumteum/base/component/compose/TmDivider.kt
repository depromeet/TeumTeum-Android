package com.teumteum.base.component.compose

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.theme.TmtmColorPalette

@Composable
fun TeumDivider() {
    Divider(
        color = TmtmColorPalette.current.ColorDivider,
        thickness = 1.dp,
        startIndent = 0.dp
    )
}