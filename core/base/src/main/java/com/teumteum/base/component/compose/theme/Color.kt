package com.teumteum.base.component.compose.theme

import android.provider.CalendarContract
import androidx.appcompat.app.AppCompatDelegate.NightMode
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
@Immutable
data class ColorPalette(
    val color_text_headline_primary: Color = Color.Unspecified,
    val color_text_headline_secondary: Color = Color.Unspecified,
    val color_text_headline_teritary: Color = Color.Unspecified,

    val color_text_body_primary: Color = Color.Unspecified,
    val color_text_body_secondary: Color = Color.Unspecified,
    val color_text_body_teritary: Color = Color.Unspecified,
    val color_text_body_quaternary: Color = Color.Unspecified,
    val color_text_body_quinary: Color = Color.Unspecified,

    val color_text_caption_primary_default: Color = Color.Unspecified,
    val color_text_caption_primary_error: Color = Color.Unspecified,
    val color_text_caption_secondary_default: Color = Color.Unspecified,
    val color_text_caption_teritary_default: Color = Color.Unspecified,
    val color_text_caption_quaternary_default: Color = Color.Unspecified,

    val color_text_button_primary_default: Color = Color.Unspecified,
    val color_text_button_primary_default02: Color = Color.Unspecified,
    val color_text_button_primary_press: Color = Color.Unspecified,
    val color_text_button_primary_disabled: Color = Color.Unspecified,
    val color_text_button_secondary_default: Color = Color.Unspecified,
    val color_text_button_alternative: Color = Color.Unspecified,

    val color_outline_level04_active: Color = Color.Unspecified,
    val color_outline_level03: Color = Color.Unspecified,

    val color_icon_level02_disabled: Color = Color.Unspecified,
    val color_icon_level02_active: Color = Color.Unspecified,
    val color_icon_level01: Color = Color.Unspecified,

    val Gray950: Color = Color.Unspecified,
    val Gray900: Color = Color.Unspecified,
    val Gray800: Color = Color.Unspecified,
    val Gray700: Color = Color.Unspecified,
    val Gray550: Color = Color.Unspecified,
    val Gray500: Color = Color.Unspecified,
    val Gray400: Color = Color.Unspecified,
    val Gray300: Color = Color.Unspecified,
    val Gray200: Color = Color.Unspecified,
    val Gray50: Color = Color.Unspecified,

    val Error300: Color = Color.Unspecified,
    val TMTMBlue200: Color = Color.Unspecified,
    val TMTMBlue400: Color = Color.Unspecified,
    val TMTMBlue500: Color = Color.Unspecified,

    val color_button_active: Color = Color.Unspecified,
    val color_button_disabled: Color = Color.Unspecified,
    val color_button_alternative: Color = Color.Unspecified,

    val GreyWhite: Color = Color.Unspecified,
    val ColorDivider: Color = Color.Unspecified,

    val elevation_color_elevation_level01: Color = Color.Unspecified,
    val elevation_color_elevation_level02: Color = Color.Unspecified,

    val color_background: Color = Color.Unspecified,
    val color_outline_level04_disabled: Color = Color.Unspecified,
    val color_outline_level01_error: Color = Color.Unspecified,
)


val ColorPalette_Dark = ColorPalette(
    color_text_headline_primary = Color(color = 0xFFF5F5F5), // gray900
    color_text_headline_secondary = Color(color = 0xFFE9E9E9), // gray800
    color_text_headline_teritary = Color(color = 0xFF828282), // gray550

    color_text_body_primary = Color(color = 0xFFF5F5F5), // gray700
    color_text_body_secondary = Color(color = 0xFFC9C9C9), // gray600
    color_text_body_teritary = Color(color = 0xFFA6A6A6), // gray600
    color_text_body_quaternary = Color(color = 0xFF828282), // gray550
    color_text_body_quinary = Color(color = 0xFF444444), // gray300

    color_text_caption_primary_default = Color(color = 0xFFF5F5F5), // gray800
    color_text_caption_primary_error = Color(color = 0xFFC4E6D), // error300
    color_text_caption_secondary_default = Color(color = 0xFFE9E9E9), // gray600
    color_text_caption_teritary_default = Color(color = 0xFF828282), // gray400
    color_text_caption_quaternary_default = Color(color = 0xFF444444), // gray300

    color_text_button_primary_default = Color(color = 0xFFFFFFFF),
    color_text_button_primary_default02 = Color(color = 0xFFF5F5F5),
    color_text_button_primary_press = Color(color = 0xFF96D7FF), // tmtm blue 200
    color_text_button_primary_disabled = Color(color = 0xFF444444),
    color_text_button_secondary_default = Color(color = 0xFF36B2FF),
    color_text_button_alternative = Color(color = 0xFFFFFFFF),

    color_outline_level04_active = Color(color = 0xFFF5F5F5),
    color_outline_level03 = Color(color = 0xFF333333),

    color_icon_level02_disabled = Color(color = 0xFF333333),
    color_icon_level02_active = Color(color = 0xFF36B2FF),
    color_icon_level01 = Color(color = 0xFFF5F5F5),

    Gray950 = Color(color = 0xFFFFFFFF), // light gray 950
    Gray900 = Color(color = 0xFFFFFFFF), // light gray 900
    Gray800 = Color(color = 0xFFF5F5F5), // light gray 50
    Gray700 = Color(color = 0xFFF5F5F5), // light gray 50
    Gray550 = Color(color = 0xFFA6A6A6), // light gray 400
    Gray500 = Color(color = 0xFF828282), // light gray 500
    Gray400 = Color(color = 0xFF828282), // light gray 500
    Gray300 = Color(color = 0xFFC9C9C9), // light gray 700
    Gray200 = Color(color = 0xFF333333), // light gray 800
    Gray50 = Color(color = 0xFF1111111), // light gray 50

    Error300 = Color(color = 0xFFFC4E6D),
    TMTMBlue200 = Color(color = 0xFF96D7FF),
    TMTMBlue400 = Color(color = 0xFF36B2FF),
    TMTMBlue500 = Color(color = 0xFF0FA4FF),

    color_button_active = Color(color = 0xFF36B2FF),
    color_button_disabled = Color(color = 0xFF292929),
    color_button_alternative = Color(color = 0xFF575757),

    GreyWhite = Color(color = 0xFF1111111),
    ColorDivider = Color(color = 0xFF212121),

    elevation_color_elevation_level01 = Color(color = 0xFF212121),
    elevation_color_elevation_level02 = Color(color = 0xFFE9E9E9),

    color_background = Color(color = 0xFF111111),

    color_outline_level04_disabled = Color(color = 0xFF333333),
    color_outline_level01_error =  Color(color =0xFFFC4E6D)
)


val ColorPalette_Light = ColorPalette(
    //headline
    color_text_headline_primary = Color(0xFF212121), //gray900
    color_text_headline_secondary = Color(0xFF333333), //gray800
     color_text_headline_teritary = Color(0xFF828282), //gray550

    //body
    color_text_body_primary = Color(color = 0xFF444444), // gray700
    color_text_body_secondary = Color(color = 0xFF575757), // gray600
    color_text_body_teritary = Color(color = 0xFF6E6E6E), // gray600
    color_text_body_quaternary = Color(color = 0xFF828282), // gray550
    color_text_body_quinary = Color(color = 0xFFC9C9C9), // gray300

    //caption
    color_text_caption_primary_default = Color(color = 0xFF333333), // gray800
    color_text_caption_primary_error = Color(color = 0xFFFC4E6D), // error300
    color_text_caption_secondary_default = Color(color = 0xFF575757), // gray600
    color_text_caption_teritary_default = Color(color = 0xFFA6A6A6), // gray400
    color_text_caption_quaternary_default = Color(color = 0xFFC9C9C9), // gray300

    //button
    color_text_button_primary_default = Color(color = 0xFFFFFFFF),
    color_text_button_primary_default02 = Color(color = 0xFF444444),
    color_text_button_primary_press = Color(color = 0xFF96D1FF), // tmtm blue 200
    color_text_button_primary_disabled = Color(color = 0xFFC9C9C9),
    color_text_button_secondary_default = Color(color = 0xFF44AEFF),
    color_text_button_alternative = Color(color = 0xFF212121),

    //outline
    color_outline_level03 = Color(color = 0xFFE9E9E9),
    color_outline_level04_active = Color(color = 0xFF212121),

    //icon
    color_icon_level02_disabled = Color(color = 0xFFDFDFDF),
    color_icon_level02_active = Color(color = 0xFF44AEFF),
    color_icon_level01 = Color(color = 0xFF212121),


    Gray950 = Color(color = 0xFF191919), // light gray 950
    Gray900 = Color(color = 0xFF212121), // light gray 900
    Gray700 = Color(color = 0xFF444444), // light gray 700
    Gray550 = Color(color = 0xFF6E6E6E), // light gray 550
    Gray500 = Color(color = 0xFF828282), // light gray 500
    Gray400 = Color(color = 0xFFA6A6A6), // light gray 400
    Gray300 = Color(color = 0xFFC9C9C9), // light gray 300
    Gray200 = Color(color = 0xFFE9E9E9), // light gray 200
    Gray50 = Color(color = 0xFFF5F5F5), // light gray 50
    Error300 = Color(color = 0xFFFC4E6D), // error 300

    TMTMBlue200 = Color(color = 0xFF96D7FF), // tmtm blue 200
    TMTMBlue400 = Color(color = 0xFF444AEFF), // tmtm blue 400
    TMTMBlue500 = Color(color = 0xFF1A9CFF), // tmtm blue 500

    color_button_active = Color(color = 0xFF44AEFF), // button active
    color_button_disabled = Color(color = 0xFFE9E9E9), // button disabled
    color_button_alternative = Color(color = 0xFFE9E9E9), // button alternative

    GreyWhite = Color(color = 0xFFFFFFFF), // grey white
    ColorDivider = Color(color = 0xFFF0F0F0), // color divider
    elevation_color_elevation_level01 = Color(color = 0xFFF5F5F5), // elevation level 1
    elevation_color_elevation_level02 = Color(color = 0xFFE9E9E9), // elevation level 2
    color_background = Color(color = 0xFFFFFFFF), // background color
    color_outline_level04_disabled = Color(color = 0xFFE9E9E9), // outline level 4 disabled
    color_outline_level01_error = Color(color = 0xFFFC4E6D), // outline level 1 error
    Gray800 = Color(color = 0xFF333333) // light gray 800
)

val TmtmColorPalette = staticCompositionLocalOf { ColorPalette() }