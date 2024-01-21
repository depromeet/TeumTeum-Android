package com.teumteum.teumteum.presentation.mypage.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
@Composable
fun EditMyInfoScreen(viewModel: SettingViewModel, navController: NavController) {
    TmScaffold(
        topbarText = stringResource(id = R.string.setting_my_info_edit_text),
        onClick = { navController.navigate(R.id.fragment_setting)}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .background(color = TmtmColorPalette.current.color_background)
        ) {
            TmMarginVerticalSpacer(size = 68)
            Text(
                text = stringResource(id = R.string.setting_my_info_edit_title1),
                style = TmTypo.current.Body2,
                color= TmtmColorPalette.current.color_text_body_quaternary,
            )
            TmMarginVerticalSpacer(size = 8)
            EditNameField()
            TmMarginVerticalSpacer(size = 20)

            Text(
                text = stringResource(id = R.string.setting_my_info_edit_title2),
                style = TmTypo.current.Body2,
                color= TmtmColorPalette.current.color_text_body_quaternary,
            )
            TmMarginVerticalSpacer(size = 8)
            EditNameField()
            TmMarginVerticalSpacer(size = 20)
            Text(
                text = stringResource(id = R.string.setting_my_info_edit_title3),
                style = TmTypo.current.Body2,
                color= TmtmColorPalette.current.color_text_body_quaternary,
            )
            TmMarginVerticalSpacer(size = 8)
            EditSignUpBox()

        }
    }
}

@Composable
fun EditSignUpBox() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .background(
            color = TmtmColorPalette.current.elevation_color_elevation_level01,
            shape = RoundedCornerShape(4.dp)
        )
    ) {
        Text(
            text = "네이버",
            color = TmtmColorPalette.current.color_text_body_quinary,
            style = TmTypo.current.Body1,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
        )
    }
}

@Composable
fun EditNameField() {
    val maxChar = 32

    OutlinedTextField(
        value = "정은아",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        placeholder = { Text(text = stringResource(id = R.string.setting_my_info_edit_placeholder1), style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary) },
        onValueChange = { newText ->
            if (newText.length <= maxChar) {
//                viewModel.updateTitle(newText)
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