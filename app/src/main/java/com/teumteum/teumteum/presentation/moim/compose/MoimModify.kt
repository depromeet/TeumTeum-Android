package com.teumteum.teumteum.presentation.moim.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.moim.BottomSheet
import com.teumteum.teumteum.presentation.moim.MoimViewModel
import com.teumteum.teumteum.presentation.mypage.editCard.EditCardLabel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.EditCardViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SheetEvent

@Composable
fun MoimModify() {


    TmScaffold(
        topbarText = stringResource(id = R.string.modify_topbar),
        onClick = {}
    ) {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .background(color = TmtmColorPalette.current.color_background)
            .wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            MoimModifyColumn()

        }

    }
}

@Composable
fun MoimModifyColumn() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = TmtmColorPalette.current.color_background)
        .padding(horizontal = 20.dp)) {

        //모임 주제
        EditCardLabel(string = stringResource(id = R.string.modify_title1))
        TmMarginVerticalSpacer(size = 8)

        //모임 이름
        EditCardLabel(string = stringResource(R.string.modify_title2))
        TmMarginVerticalSpacer(size = 8)


    }
}

@Composable
fun EditMeetingBottomBox(
    text: String,
    viewModel: MoimViewModel,
    bottomSheet: BottomSheet
) {
    val displayText = if (text.isBlank()) stringResource(id = R.string.setting_edit_card_placeholder3) else text
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = TmtmColorPalette.current.elevation_color_elevation_level01,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                viewModel.updateBottomSheet(bottomSheet)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = displayText,
                color = if(text.isNotBlank()) TmtmColorPalette.current.color_text_body_primary else TmtmColorPalette.current.color_text_body_quinary,
                style = TmTypo.current.Body1,
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_down_l),
                contentDescription = null
            )

        }
    }
    TmMarginVerticalSpacer(size = 20)
}