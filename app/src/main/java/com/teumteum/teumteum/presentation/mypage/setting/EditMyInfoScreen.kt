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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel

@Composable
fun EditMyInfoScreen(viewModel: SettingViewModel, navController: NavController) {
    TmScaffold(
        topbarText = stringResource(id = R.string.setting_my_info_edit_text),
        onClick = {
            viewModel.updateUserInfo()
            navController.popBackStack()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background)
        ) {
            TmMarginVerticalSpacer(size = 68)
            Text(
                text = stringResource(id = R.string.setting_my_info_edit_title1),
                style = TmTypo.current.Body2,
                color= TmtmColorPalette.current.color_text_body_quaternary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            TmMarginVerticalSpacer(size = 8)
            EditNameField(viewModel)
            TmMarginVerticalSpacer(size = 20)

            Text(
                text = stringResource(id = R.string.setting_my_info_edit_title2),
                style = TmTypo.current.Body2,
                color= TmtmColorPalette.current.color_text_body_quaternary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            TmMarginVerticalSpacer(size = 8)
            EditBirthField(viewModel)
            TmMarginVerticalSpacer(size = 20)
            Text(
                text = stringResource(id = R.string.setting_my_info_edit_title3),
                style = TmTypo.current.Body2,
                color= TmtmColorPalette.current.color_text_body_quaternary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            TmMarginVerticalSpacer(size = 8)
            EditSignUpBox(viewModel)

        }
    }
}



@Composable
fun EditSignUpBox(viewModel: SettingViewModel) {
    val text by viewModel.userAuth.collectAsState()
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(horizontal =20.dp)
        .background(
            color = TmtmColorPalette.current.elevation_color_elevation_level01,
            shape = RoundedCornerShape(4.dp)
        )
    ) {
        Text(
            text = text,
            color = TmtmColorPalette.current.color_text_body_quinary,
            style = TmTypo.current.Body1,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
        )
    }
}

@Composable
fun EditNameField(viewModel: SettingViewModel) {
    val maxChar = 10
    val userName by viewModel.userName.collectAsState()

    OutlinedTextField(
        value = userName,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .wrapContentHeight(),
        placeholder = { Text(text = stringResource(id = R.string.setting_my_info_edit_placeholder1), style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary) },
        onValueChange = { newText ->
            if (newText.length <= maxChar) {
                viewModel.updateUserName(newText)
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = TmtmColorPalette.current.color_text_body_primary,
            focusedBorderColor = TmtmColorPalette.current.elevation_color_elevation_level01,
            unfocusedBorderColor = TmtmColorPalette.current.elevation_color_elevation_level01,
            unfocusedLabelColor = TmtmColorPalette.current.color_text_body_quinary,
            focusedLabelColor = TmtmColorPalette.current.color_text_body_quinary,
            backgroundColor = TmtmColorPalette.current.elevation_color_elevation_level01,
            cursorColor = TmtmColorPalette.current.TMTMBlue500,
        ),
    )
}

@Composable
fun EditBirthField(viewModel: SettingViewModel) {
    val maxChar = 8
    val userBirth by viewModel.userBirthDate.collectAsState()

    OutlinedTextField(
        value = userBirth,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .wrapContentHeight(),
        placeholder = { Text(text = stringResource(id = R.string.setting_my_info_edit_placeholder1), style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary) },
        onValueChange = { newText ->
            if (newText.length <= maxChar) {
                viewModel.updateUserBirthDate(newText)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = TmtmColorPalette.current.color_text_body_primary,
            focusedBorderColor = TmtmColorPalette.current.elevation_color_elevation_level01,
            unfocusedBorderColor = TmtmColorPalette.current.elevation_color_elevation_level01,
            unfocusedLabelColor = TmtmColorPalette.current.color_text_body_quinary,
            focusedLabelColor = TmtmColorPalette.current.color_text_body_quinary,
            backgroundColor = TmtmColorPalette.current.elevation_color_elevation_level01,
            cursorColor = TmtmColorPalette.current.TMTMBlue500,
        ),
    )
}