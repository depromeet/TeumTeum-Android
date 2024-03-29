package com.teumteum.teumteum.presentation.moim.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teumteum.base.component.compose.TeumDivider
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.moim.MoimViewModel

@Composable
fun MoimAddress(viewModel: MoimViewModel, navController: NavController, onClick: () -> Unit) {
    val address by viewModel.address.collectAsState()
    val detailAddress by viewModel.detailAddress.collectAsState()

    TmScaffold(onClick = {onClick()}) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            TmMarginVerticalSpacer(size = 48)
            CreateMoimTitle(string = stringResource(id = R.string.moim_address_title))
            TmMarginVerticalSpacer(size = 28)
            MoimAddress1Column(viewModel, navController)
            TmMarginVerticalSpacer(size = 20)
            MoimAddress2Column(viewModel)
            Spacer(Modifier.weight(1f))

            TeumDivider()
            MoimCreateBtn(text = stringResource(id = R.string.moim_next_btn), isEnabled = !address.isNullOrEmpty() && detailAddress.isNotEmpty(), viewModel = viewModel)
            TmMarginVerticalSpacer(size = 24)
        }
    }

}

@Composable
fun MoimAddress1Column(viewModel: MoimViewModel, navController: NavController) {
    val address by viewModel.address.collectAsState()
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.moim_address_label1),
            style = TmTypo.current.Body2,
            color = TmtmColorPalette.current.color_text_body_quaternary
        )
        TmMarginVerticalSpacer(size = 8)
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = TmtmColorPalette.current.elevation_color_elevation_level01,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                navController.navigate(R.id.action_moimFragment_to_fragment_web_view)
            }
        ) {
            Text(
                text = address ?: stringResource(id = R.string.moim_address_placeholdler1),
                color = TmtmColorPalette.current.color_text_body_quinary,
                style = TmTypo.current.Body1,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun MoimAddress2Column(viewModel: MoimViewModel) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.moim_address_label2),
            style = TmTypo.current.Body2,
            color = TmtmColorPalette.current.color_text_body_quaternary
        )
        TmMarginVerticalSpacer(size = 8)
        MoimAddressInputField(viewModel = viewModel, placeHolder = stringResource(id = R.string.moim_address_placeholder2))
    }
}

@Composable
fun MoimAddressInputField(
    placeHolder:String,
    viewModel: MoimViewModel
    ) {
    val text by viewModel.detailAddress.collectAsState()

    OutlinedTextField(
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        placeholder = { Text(text =placeHolder, style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary)},
        onValueChange = { newText ->
            viewModel.updateDetailAddress(newText)
        },
        singleLine = true,
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