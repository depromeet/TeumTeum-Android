package com.teumteum.base.component.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@Composable
fun TeumDividerThick(int : Int) {
    Divider(
        color = TmtmColorPalette.current.ColorDivider,
        thickness = int.dp,
        startIndent = 0.dp
    )
}

@Composable
fun TeumDividerHorizontalThick(int : Int, margin: Int) {
    Divider(
        color = TmtmColorPalette.current.ColorDivider,
        thickness = int.dp,
        modifier = Modifier.fillMaxWidth().padding(horizontal = margin.dp),
    )
}