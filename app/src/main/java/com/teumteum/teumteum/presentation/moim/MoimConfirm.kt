package com.teumteum.teumteum.presentation.moim

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R

@Preview
@Composable
fun MoimConfirm() {
    TmScaffold(
        topbarText = stringResource(id = R.string.moim_name_title),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "테스트",
                style= TmTypo.current.Body3,
                color = TmtmColorPalette.current.color_text_caption_teritary_default,
                modifier = Modifier
                    .width(284.dp)
            )

        }
    }
}