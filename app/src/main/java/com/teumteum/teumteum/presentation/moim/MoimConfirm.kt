package com.teumteum.teumteum.presentation.moim

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import com.google.accompanist.pager.HorizontalPager
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.teumteum.base.component.compose.TeumDivider
import com.teumteum.base.component.compose.TeumDividerThick
import com.teumteum.base.component.compose.TmIndicator
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MoimConfirm(viewModel: MoimViewModel) {
    TmScaffold(
        topbarText = stringResource(id = R.string.moim_confirm_appbar),
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background)
                .verticalScroll(scrollState)
        ) {
            MoimPhotoPager(viewModel)
            MoimConfirmInfo(viewModel)
            TmMarginVerticalSpacer(size = 32)
            TeumDividerThick(int = 8)
            TmMarginVerticalSpacer(size = 20)
            MoimHostRow()
            TmMarginVerticalSpacer(size = 20)
            TeumDividerThick(int = 8)
            MoimConfirmIntroColumn(viewModel)
            Spacer(modifier = Modifier.weight(1f))
            TeumDivider()
            MoimCreateBtn(text = stringResource(id = R.string.moim_next_btn), viewModel = viewModel)
            TmMarginVerticalSpacer(size = 24)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MoimPhotoPager(viewModel : MoimViewModel) {
    val imageUri by viewModel.imageUri.collectAsState()
    val pagerState = rememberPagerState()
    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(276.dp),
            count = imageUri.size,
            state = pagerState
        ) {page->
            val uri = imageUri[page]
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds()
            )
        }
        if (imageUri.size > 1) {
            TmIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                activeColor = TmtmColorPalette.current.color_button_active,
                inactiveColor = TmtmColorPalette.current.color_icon_level02_disabled,
                indicatorHeight = 4.dp
            )
        }
    }
}

@Composable
fun MoimConfirmInfo(viewModel: MoimViewModel) {
    val title by viewModel.title.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = title,
                style = TmTypo.current.HeadLine3,
                modifier = Modifier
                    .width(320.dp)
                    .align(Alignment.Top),
                color = TmtmColorPalette.current.color_text_headline_primary
            )
            TmMarginHorizontalSpacer(size = 8)
            Image(
                painterResource(id = R.drawable.icon_share),
                contentDescription = null,
                modifier= Modifier
                    .size(24.dp)
                    .offset(y = 5.dp)
            )
        }
        TmMarginVerticalSpacer(size = 16)
        MoimInfoCard(viewModel)
    }
}

@Composable
fun MoimInfoCard(viewModel: MoimViewModel) {
    val topic by viewModel.topic.collectAsState()
    val people by viewModel.people.collectAsState()
    val date by viewModel.date.collectAsState()
    val time by viewModel.time.collectAsState()
    val address by viewModel.address.collectAsState()
    val detailAddress by viewModel.detailAddress.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(
                color = TmtmColorPalette.current.elevation_color_elevation_level01,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MoimCardRow(title = stringResource(id = R.string.moim_confirm_title1), text = "$date $time")
            TmMarginVerticalSpacer(size = 4)
            MoimCardRow(title = stringResource(id = R.string.moim_confirm_title2), text = viewModel.getTopicTitle(topic))
            TmMarginVerticalSpacer(size = 4)
            MoimCardRow(title = stringResource(id = R.string.moim_confirm_title3), text = "${people}명")
            TmMarginVerticalSpacer(size = 4)
            MoimCardRow(title = stringResource(id = R.string.moim_confirm_title4), text = "$address $detailAddress")

        }

    }
}

@Composable
fun MoimCardRow(title: String, text: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = TmTypo.current.Body2,
            color = TmtmColorPalette.current.color_text_body_secondary
        )

        Text(
            text = text,
            style = TmTypo.current.Body2,
            color = TmtmColorPalette.current.color_text_body_secondary
        )

    }
}

@Composable
fun MoimHostRow() {
    Row(
        modifier = Modifier
            .width(147.dp)
            .height(40.dp)
            .padding(start = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(color = TmtmColorPalette.current.elevation_color_elevation_level01)
        ) {
            Image(painter = painterResource(id = R.drawable.ic_dog),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }

        Column(
            modifier=Modifier
                .wrapContentSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "주최자 이름",
                style = TmTypo.current.HeadLine6,
                color = TmtmColorPalette.current.color_text_body_primary
            )
            Text(
                text = "주최자 직업",
                style = TmTypo.current.Caption1,
                color = TmtmColorPalette.current.color_text_body_secondary,
                modifier = Modifier.padding(start = 1.dp)
            )
        }
    }
}

@Composable
fun MoimConfirmIntroColumn(viewModel: MoimViewModel) {
    val introduce by viewModel.introduction.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = R.string.moim_confirm_title5),
            style = TmTypo.current.HeadLine5,
            color = TmtmColorPalette.current.color_text_body_primary,
        )
        TmMarginVerticalSpacer(size = 16)
        Text(
            text = introduce,
            style = TmTypo.current.Body1,
            color = TmtmColorPalette.current.color_text_body_secondary,
        )
    }

}


