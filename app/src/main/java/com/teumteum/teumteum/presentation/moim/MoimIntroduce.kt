package com.teumteum.teumteum.presentation.moim

import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
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
        TmMarginVerticalSpacer(size = 28)

        MoimIntroColumn()
        TmMarginVerticalSpacer(size = 20)
        TeumDivider()
        TmMarginVerticalSpacer(size = 20)

        MoimPhotoColumn()
        Spacer(modifier = Modifier.weight(1f))
        TeumDivider()
        MoimCreateBtn(text = "다음")
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
        TmMarginVerticalSpacer(size = 24)
        MoimSystemText(text = R.string.moim_introduce_system_text)

    }
}

@Composable
fun MoimSystemText(@StringRes text: Int) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_system),
            contentDescription =null,
            modifier = Modifier.padding(vertical =0.dp)
        )
        TmMarginHorizontalSpacer(size = 6)
        Text(
            text = stringResource(id = text),
            style= TmTypo.current.Body3,
            color = TmtmColorPalette.current.color_text_caption_teritary_default,
            modifier = Modifier
                .width(284.dp)
        )

    }
}

@Composable
fun MoimInputField() {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(272.dp),
        placeholder = { Text(text ="모임을 소개해 주세요", style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary) },
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
fun MoimPhotoColumn() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = TmtmColorPalette.current.GreyWhite),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal=20.dp)
            .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.moim_introduce_title1_photo), style= TmTypo.current.SubTitle1, color = TmtmColorPalette.current.color_text_body_primary)
            TmMarginHorizontalSpacer(size = 6)
            Text(text = stringResource(id = R.string.moim_introduce_title2_photo), style= TmTypo.current.Body2, color= TmtmColorPalette.current.color_text_headline_teritary)
        }
        TmMarginVerticalSpacer(size = 8)
        MoimPhotoInput()
        TmMarginVerticalSpacer(size = 8)
        MoimSystemText(text = R.string.moim_introduce_system_photo)
    }
}

@Composable
fun MoimPhotoInput() {
    Image(
        painter = painterResource(id = R.drawable.ic_input_photo),
        contentDescription = null
    )

}

@Preview
@Composable
fun previewPhoto() {
    MoimIntroduce()
}