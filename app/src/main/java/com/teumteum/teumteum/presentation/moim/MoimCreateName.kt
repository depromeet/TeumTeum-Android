package com.teumteum.teumteum.presentation.moim

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TeumDivider
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R

@Composable
fun MoimCreateName(viewModel: MoimViewModel, onClick: () ->Unit) {
    val title by viewModel.title.collectAsState()
    TmScaffold(onClick = {onClick()}) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            TmMarginVerticalSpacer(size = 48)
            CreateMoimTitle(string = stringResource(id = R.string.moim_name_title))
            TmMarginVerticalSpacer(size = 28)
            CreateNameContent(viewModel)
            Spacer(modifier = Modifier.weight(1f))
            TeumDivider()
            MoimCreateBtn(text= stringResource(id = R.string.moim_next_btn), viewModel = viewModel, isEnabled = title.length in 2..32)
            TmMarginVerticalSpacer(size = 24)
        }
    }
}

@Composable
fun CreateNameContent(viewModel: MoimViewModel) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 20.dp)
    ) {
        Text(text = "모임 이름", style= TmTypo.current.Body2, color= TmtmColorPalette.current.color_text_body_quaternary)
        TmMarginVerticalSpacer(size = 8)
        MoimNameTextField(viewModel =  viewModel, placeHolder = stringResource(id = R.string.moim_name_title))
    }

}

@Composable
fun MoimNameTextField(viewModel: MoimViewModel, placeHolder: String) {
    val text by viewModel.title.collectAsState()
    val maxChar = 32

    OutlinedTextField(
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        placeholder = { Text(text =placeHolder, style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary)},
        onValueChange = { newText ->
            if (newText.length <= maxChar) {
                viewModel.updateTitle(newText)
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