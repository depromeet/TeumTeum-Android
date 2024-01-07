package com.teumteum.teumteum.presentation.moim

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.teumteum.R
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette

@Composable
fun MoimIntroduce() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .background(color = TmtmColorPalette.current.GreyWhite),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        CreateMoimTitle(string="모임을 소개해 주세요")
        TeumDivider()
        MoimCreateBtn(text = "다음")
        TmMarginVerticalSpacer(size = 28)
        MoimInputField()
        TmMarginVerticalSpacer(size = 24)

    }
}

@Composable
fun MoimIntroColumn() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        MoimInputField()

    }
}

@Composable
fun MoimInputField() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .height(272.dp),
        placeholder = { Text(text ="모임을 소개해 주세요", style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary, modifier = Modifier.padding(horizontal = 16.dp)) },
        onValueChange = { text = it },
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

@Composable
fun MoimPhotoInput() {

}