package com.teumteum.teumteum.presentation.moim

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R

@Composable
fun MoimAddress() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = TmtmColorPalette.current.GreyWhite),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        CreateMoimTitle(string = stringResource(id = R.string.moim_address_title))
    }

}

@Composable
fun MoimAddressColumn() {
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
    }
}