package com.teumteum.teumteum.presentation.compose.theme



import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.teumteum.teumteum.R.font


val Pretendard = FontFamily(
    Font(font.pretendard_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
    Font(font.pretendard_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
    Font(font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    Font(font.pretendard_light, FontWeight.Light, FontStyle.Normal),
    Font(font.pretendard_extralight, FontWeight.ExtraLight, FontStyle.Normal),
)

data class Type(

    val SubTitle1:  TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(600),
        fontSize =  14.sp,
        lineHeight = 20.sp,
    ),

    val HeadLine1: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(700),
        fontSize =  32.sp,
        lineHeight = 36.sp,
    ),

    val HeadLine2: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(700),
        fontSize =  28.sp,
        lineHeight = 36.sp,
    ),

    val HeadLine3: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(700),
        fontSize =  24.sp,
        lineHeight = 32.sp,
    ),

    val HeadLine4: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(700),
        fontSize =  20.sp,
        lineHeight = 28.sp,
    ),

    val HeadLine5: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(700),
        fontSize =  18.sp,
        lineHeight = 24.sp,
    ),

    val HeadLine6: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(600),
        fontSize =  16.sp,
        lineHeight = 22.sp,
    ),

    val HeadLine7: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(500),
        fontSize =  16.sp,
        lineHeight = 22.sp,
    ),

    val Body1: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight(400),
        fontSize =  16.sp,
        lineHeight = 22.sp,
    ),

    val Body2: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight(400),
        fontSize =  14.sp,
        lineHeight = 22.sp,
    ),

    val Body3: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight(400),
        fontSize =  12.sp,
        lineHeight = 18.sp,
    ),

    val Caption1: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight(600),
        fontSize =  11.sp,
        lineHeight = 16.sp,
    ),

    val Caption2: TextStyle = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight(500),
        fontSize =  10.sp,
        lineHeight = 12.sp,
    ),
)
val TmTypo = staticCompositionLocalOf { Type() }