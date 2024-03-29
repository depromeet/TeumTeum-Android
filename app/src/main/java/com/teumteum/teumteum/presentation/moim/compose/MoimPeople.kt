package com.teumteum.teumteum.presentation.moim.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TeumDivider
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.moim.MoimViewModel

@Composable
fun MoimPeople(viewModel: MoimViewModel, onClick: () -> Unit) {
    val people by viewModel.people.collectAsState()
    TmScaffold(onClick = { onClick()}) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            TmMarginVerticalSpacer(size = 48)
            CreateMoimTitle(string = stringResource(id = R.string.moim_people_title))
            TmMarginVerticalSpacer(size = 28)

            PeopleContent(viewModel)
            TmMarginVerticalSpacer(size = 39)
            PeopleSystemText()

            Spacer(modifier= Modifier.weight(1f))
            TeumDivider()
            MoimCreateBtn(text = stringResource(id = R.string.moim_next_btn), isEnabled = people in 3..6, viewModel = viewModel)
            TmMarginVerticalSpacer(size = 24)
        }
    }
}

@Composable
fun PeopleContent(
    viewModel: MoimViewModel
) {
    val people by viewModel.people.collectAsState()
    Row(modifier = Modifier
        .width(181.dp)
        .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_system_minus),
            contentDescription = "image description",
            contentScale = ContentScale.None,
            modifier = Modifier.clickable {
                viewModel.downPeople()
            }
        )

        Box(modifier = Modifier
            .width(77.dp)
            .height(80.dp)
            .background(
                color = TmtmColorPalette.current.elevation_color_elevation_level01,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${people}명",
                style= TmTypo.current.HeadLine2,
                color= TmtmColorPalette.current.color_text_button_secondary_default,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_system_plus),
            contentDescription = "image description",
            contentScale = ContentScale.None,
            modifier = Modifier.clickable {
                viewModel.upPeople()
            }
        )
    }
}

@Composable
fun PeopleSystemText() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .padding(horizontal = 20.dp)
        .background(
            color = TmtmColorPalette.current.elevation_color_elevation_level01,
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_system_fill),
                contentDescription =null,
                modifier = Modifier.padding(vertical =0.dp)
            )
            TmMarginHorizontalSpacer(size = 6)
            Text(
                text = stringResource(id = R.string.moim_peoople_system),
                style= TmTypo.current.Body3,
                color = TmtmColorPalette.current.color_text_caption_teritary_default,
                modifier = Modifier
                    .width(202.dp)
            )
        }
    }
}
