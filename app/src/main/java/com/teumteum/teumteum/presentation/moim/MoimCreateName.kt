package com.teumteum.teumteum.presentation.moim

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.teumteum.R
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette

@Composable
fun MoimCreateName() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .background(color = TmtmColorPalette.current.GreyWhite),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        CreateMoimTitle(string = "모임 이름을 작성해 주세요")
        TmMarginVerticalSpacer(size = 28)
        CreateNameContent()

        TeumDivider()
        MoimCreateBtn(text = "다음")
        TmMarginVerticalSpacer(size = 24)
    }
}

@Composable
fun CreateNameContent() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 20.dp)
    ) {
        Text(text = "모임 이름", style= TmTypo.current.Body2, color= TmtmColorPalette.current.color_text_body_quaternary)
        TmMarginVerticalSpacer(size = 8)
        CreateTextField(placeHolder = "모임 이름을 작성해주세요")
    }

}

@Composable
fun CreateTextField(placeHolder: String) {
    var text by remember { mutableStateOf("") }
    val maxChar = 32

    OutlinedTextField(
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        placeholder = { Text(text =placeHolder, style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary)},
        onValueChange = { newText ->
            if (newText.length <= maxChar) {
                text = newText
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = TmtmColorPalette.current.color_text_body_primary,
            focusedBorderColor = TmtmColorPalette.current.elevation_color_elevation_level01,
            unfocusedBorderColor = TmtmColorPalette.current.elevation_color_elevation_level01,
            unfocusedLabelColor = TmtmColorPalette.current.color_text_body_quinary,
            focusedLabelColor = TmtmColorPalette.current.color_text_body_quinary,
            backgroundColor = TmtmColorPalette.current.elevation_color_elevation_level01
        ),
    )
}

@Preview
@Composable
fun preview2() {
    MoimCreateName()
}