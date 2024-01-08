package com.teumteum.base.component.compose.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimen(
    val defaultHorizontal: Dp = 20.dp
)

val TmDimen = staticCompositionLocalOf { Dimen() }