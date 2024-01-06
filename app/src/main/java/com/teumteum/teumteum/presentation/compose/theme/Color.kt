package com.teumteum.teumteum.presentation.compose.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

data class ColorPalette_Light(
    val Gray950: Color = Color(0xFF191919),
    //headline
    val color_text_headline_primary: Color = Color(0xFF212121), //gray900
    val color_text_headline_secondary: Color = Color(0xFF333333), //gray800
    val color_text_headline_teritary: Color = Color(0xFF828282), //gray550

    //body
    val color_text_body_primary: Color = Color(0xFF444444), // gray700
    val color_text_body_secondary: Color = Color(0xFF575757), //gray600
    val color_text_body_quaternary : Color = Color(0xFF828282), //gray550
    val color_text_body_quinary: Color = Color(0xFFC9C9C9), //gray300

    //caption
    val color_text_caption_primary_default: Color =Color(0xFF333333), //gray800
    val color_text_caption_primary_error: Color = Color(0xFFFC4E6D), //error300
    val color_text_caption_secondary_default: Color = Color(0xFF575757), //gray600
    val color_text_caption_teritary_default: Color = Color(0xFFA6A6A6), //gray400
    val color_text_caption_quaternary_default: Color = Color(0xFFC9C9C9), //gray300

    //button
    val color_text_button_primary_default: Color = Color(0xFFFFFFFF),
    val color_text_button_primary_press: Color = Color(0xFF96D1FF), //tmtm blue 200
    val color_text_button_primary_disabled: Color = Color(0xFFC9C9C9),
    val color_text_button_secondary_default: Color = Color(0xFF44AEF),
    val color_text_button_alternative: Color = Color(0xFF212121),


    val Gray700: Color = Color(0xFF444444),
    val Gray550: Color = Color(0xFF6E6E6E),
    val Gray500: Color = Color(0xFF828282),
    val Gray400: Color = Color(0xFFA6A6A6),
    val Gray300: Color = Color(0xFFC9C9C9),
    val Gray200: Color = Color(0xFFE9E9E9),
    val Gray50: Color = Color(0xFFF5F5F5),

    val Error300: Color = Color(0xFFFC4E6D),
    val TMTMBlue200: Color = Color(0xFF96D7FF),
    val TMTMBlue400: Color = Color(0xFF36B2FF),
    val color_button_active: Color = Color(0xFF44AEFF),
    val GreyWhite: Color = Color(0xFFFFFFFF),
    val ColorDivider: Color = Color(0xFFF0F0F0),
    val elevation_color_elevation_level01: Color = Color(0xFFF5F5F5),
)

val TmtmColorPalette = staticCompositionLocalOf { ColorPalette_Light() }