package com.teumteum.teumteum.presentation.moim

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TeumDivider
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun MoimDateTime() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = TmtmColorPalette.current.GreyWhite),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        CreateMoimTitle(string = stringResource(id = R.string.moim_datetime_title))
        TmMarginVerticalSpacer(size = 28)
        MoimDateColumn()
        TmMarginVerticalSpacer(size = 20)
        MoimTimeColumn()
        Spacer(modifier = Modifier.weight(1f))
        TeumDivider()
//        MoimCreateBtn(text = "다음")
        TmMarginVerticalSpacer(size = 24)
    }
}

@Composable
fun MoimDateColumn() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 20.dp)
    ) {
        Text(text = stringResource(id = R.string.moim_datetime_label1), style= TmTypo.current.Body2, color= TmtmColorPalette.current.color_text_body_quaternary)
        TmMarginVerticalSpacer(size = 8)
        MoimDateInputField(placeHolder = stringResource(id = R.string.moim_datetime_placeholder1))

    }
}

@Composable
fun MoimTimeColumn() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.moim_datetime_label2),
            style = TmTypo.current.Body2,
            color = TmtmColorPalette.current.color_text_body_quaternary
        )
        TmMarginVerticalSpacer(size = 8)
        MoimDateInputField(placeHolder = stringResource(id = R.string.moim_datetime_placeholder2))
    }
}

@Composable
fun MoimDateInputField(placeHolder:String) {
    var text by remember { mutableStateOf("") }
    var dayOfWeek by remember { mutableStateOf<String?>(null) }

    OutlinedTextField(
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        placeholder = { Text(text =placeHolder, style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary)},
        onValueChange = { newText ->
            text = newText
            dayOfWeek = formatDate(newText)
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
    if (dayOfWeek != null) {
        Text(text = "$text $dayOfWeek", style=TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_primary)
    }
}

fun formatTime(input: String): String? {
    return try {
        val localTime = LocalTime.parse(input, DateTimeFormatter.ofPattern("HHmm"))
        localTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
    } catch (e: Exception) {
        null
    }
}

fun formatDate(input: String): String? {
    return try {
        val parsedDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("MM월 dd일"))
        parsedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    } catch (e: Exception) {
        null
    }
}