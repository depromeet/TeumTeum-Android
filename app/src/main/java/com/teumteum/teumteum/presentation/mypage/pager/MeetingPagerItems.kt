package com.teumteum.teumteum.presentation.mypage.pager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.Meeting
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MeetingDummy1
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun MeetingItem(meeting: com.teumteum.domain.entity.Meeting) {
    val formattedTime = formatDateTime(meeting.date)
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(62.dp)
        .clickable { }
        .background(
            color = TmtmColorPalette.current.elevation_color_elevation_level01,
            shape = RoundedCornerShape(4.dp)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = meeting.name,
                    style = TmTypo.current.HeadLine7,
                    color = TmtmColorPalette.current.color_text_headline_primary
                )
                Text(
                    text = formattedTime,
                    style = TmTypo.current.Body3,
                    color = TmtmColorPalette.current.color_text_body_quaternary
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right_l),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

fun formatDateTime(dateTime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREAN)
    val outputFormat = SimpleDateFormat("M월 d일 aa h:mm", Locale.KOREAN)

    val date = inputFormat.parse(dateTime)
    return outputFormat.format(date ?: return "")
}

@Composable
fun NoMoimItems(
    commingMoim: Boolean = true,
    navController: NavController
) {
    val textId = if(commingMoim) R.string.setting_pager1_no_moim_text else R.string.setting_pager1_no_moim_text2
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(62.dp)
        .clickable { navController.navigate(R.id.fragment_home)}
        .background(
            color = TmtmColorPalette.current.elevation_color_elevation_level01,
            shape = RoundedCornerShape(4.dp)
        )
    ) {
        if(!commingMoim) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = textId),
                    style = TmTypo.current.HeadLine7,
                    color = TmtmColorPalette.current.color_text_headline_primary
                )
                Text(
                    text = stringResource(id = R.string.setting_pager1_go_to_moim),
                    style = TmTypo.current.Body1,
                    color = TmtmColorPalette.current.color_text_button_secondary_default
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = textId),
                    style = TmTypo.current.HeadLine7,
                    color = TmtmColorPalette.current.color_text_headline_primary,
                )
            }
        }
    }
    TmMarginVerticalSpacer(size = 16)
}
@Composable
fun MyMoimItems(meeting: com.teumteum.domain.entity.Meeting) {
    val formattedTime = formatDateTime(meeting.date)
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(62.dp)
        .clickable { }
        .background(
            color = TmtmColorPalette.current.elevation_color_elevation_level01,
            shape = RoundedCornerShape(4.dp)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = meeting.name,
                    style = TmTypo.current.HeadLine7,
                    color = TmtmColorPalette.current.color_text_headline_primary
                )
                Row(modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = formattedTime,
                        style = TmTypo.current.Body3,
                        color = TmtmColorPalette.current.color_text_body_quaternary
                    )
                    TmMarginHorizontalSpacer(size = 12)
                    MyMoimBadge()
                }
            }
            Image(
                painter = painterResource(id = R.drawable.ic_pencil_fill),
                contentDescription = null,
            )
        }
    }
    TmMarginVerticalSpacer(size = 16)
}

@Composable
fun MyMoimBadge() {
    Box(modifier = Modifier
        .width(115.dp)
        .height(24.dp)
        .clickable { }
        .background(
            color = TmtmColorPalette.current.elevation_color_elevation_level02,
            shape = RoundedCornerShape(4.dp)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_crown_fill),
                contentDescription = null,
            )
            TmMarginHorizontalSpacer(size = 4)
            Text(
                text = stringResource(id = R.string.setting_my_moim_badge),
                style = TmTypo.current.Body3,
                color = TmtmColorPalette.current.color_text_body_quaternary
            )
        }
    }

}