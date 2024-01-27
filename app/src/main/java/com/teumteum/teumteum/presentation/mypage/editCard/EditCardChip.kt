package com.teumteum.teumteum.presentation.mypage.editCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.EditCardViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterestsChips(interests: List<String>, onClick: () -> Unit, viewModel: EditCardViewModel) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            interests.forEach { interest ->
                EditCardChipDefault(interest) { viewModel.removeInterestField(interest) }
                TmMarginHorizontalSpacer(size = 8)
            }
            EditCardChipPlus(onClick)
        }
}

@Composable
fun EditCardChipDefault(text: String, onDeleteClick: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .border(
                width = 1.dp,
                color = TmtmColorPalette.current.color_text_body_teritary,
                shape = RoundedCornerShape(200.dp)
            )
            .height(48.dp)
            .padding(bottom = 4.dp, top = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .height(40.dp)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = TmTypo.current.Body1,
                color = TmtmColorPalette.current.color_text_body_teritary,
                modifier = Modifier.alignByBaseline()
            )
            Image(
                painterResource(id = R.drawable.ic_close_fill),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .alignByBaseline()
                    .padding(top = 2.dp)
                    .clickable { onDeleteClick() }
            )
        }
    }
}

@Composable
fun EditCardChipPlus(onClick: () ->Unit) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .background(
                color = TmtmColorPalette.current.color_button_active,
                shape = RoundedCornerShape(200.dp)
            )
            .border(
                width = 1.dp,
                color = TmtmColorPalette.current.color_button_active,
                shape = RoundedCornerShape(200.dp)
            )
            .clickable { onClick() }
            .height(48.dp)
            .padding(bottom = 4.dp, top = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .height(40.dp)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.setting_edit_chip_plus),
                style = TmTypo.current.Body1,
                color = TmtmColorPalette.current.color_text_button_primary_default,
                modifier = Modifier.alignByBaseline()
            )
            Image(
                painterResource(id = R.drawable.ic_plus_fill),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .alignByBaseline()
                    .padding(top = 2.dp)
            )
        }
    }
}