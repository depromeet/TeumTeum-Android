package com.teumteum.teumteum.presentation.moim.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.teumteum.base.component.compose.TeumDivider
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.moim.BottomSheet
import com.teumteum.teumteum.presentation.moim.MoimViewModel


@Composable
fun MoimDateTime(viewModel: MoimViewModel, onClick: ()->Unit) {
    val time by viewModel.time.collectAsState()
    val date by viewModel.date.collectAsState()
    TmScaffold(onClick = {onClick()}) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            TmMarginVerticalSpacer(size = 48)
            CreateMoimTitle(string = stringResource(id = R.string.moim_datetime_title))
            TmMarginVerticalSpacer(size = 28)
            MoimDateColumn(viewModel)
            TmMarginVerticalSpacer(size = 20)
            MoimTimeColumn(viewModel)
            Spacer(modifier = Modifier.weight(1f))
            TeumDivider()
            MoimCreateBtn(
                text = stringResource(id = R.string.moim_next_btn),
                viewModel = viewModel,
                isEnabled = time.isNotEmpty() && date.isNotEmpty())
            TmMarginVerticalSpacer(size = 24)
        }
    }
}

private fun updateDateIfComplete(year: String, month: String, day: String, viewModel: MoimViewModel) {
    if (year.length == 4 && month.length == 2 && day.length == 2) {
        val inputDate = "$year$month$day"
        viewModel.updateDate(inputDate)
    }
}

@Composable
fun MoimDateColumn(viewModel: MoimViewModel) {

    var yearText by remember { mutableStateOf("") }
    var monthText by remember { mutableStateOf("") }
    var dayText by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 20.dp)
    ) {
        Text(text = stringResource(id = R.string.moim_datetime_label1), style= TmTypo.current.Body2, color= TmtmColorPalette.current.color_text_body_quaternary)
        TmMarginVerticalSpacer(size = 8)

        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = yearText,
                modifier = Modifier
                    .width(78.dp)
                    .wrapContentHeight(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                placeholder = { Text(text ="YYYY", style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary)},
                onValueChange = { newText ->
                    yearText= newText.take(4)
                    updateDateIfComplete(yearText, monthText, dayText, viewModel)
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
            TmMarginHorizontalSpacer(size = 8)

            OutlinedTextField(
                value = monthText,
                modifier = Modifier
                    .width(70.dp)
                    .wrapContentHeight(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                placeholder = { Text(text ="MM", style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary)},
                onValueChange = { newText ->
                    monthText= newText.take(2)
                    updateDateIfComplete(yearText, monthText, dayText, viewModel)
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
            TmMarginHorizontalSpacer(size = 8)

            OutlinedTextField(
                value = dayText,
                modifier = Modifier
                    .width(70.dp)
                    .wrapContentHeight(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                placeholder = { Text(text ="DD", style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary)},
                onValueChange = { newText ->
                    dayText= newText.take(2)
                    updateDateIfComplete(yearText, monthText, dayText, viewModel)
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

    }
}

private fun updateTimeIfComplete(hour: String, minute: String, viewModel: MoimViewModel) {
    if (hour.isNotEmpty() && minute.isNotEmpty()) {
        val formattedHour = if (hour.length == 1) "0$hour" else hour
        val formattedMinute = if (minute.length == 1) "0$minute" else minute

        val time = "$formattedHour:$formattedMinute"
        viewModel.updateTime(time)
    }
}

@Composable
fun MoimTimeColumn(viewModel: MoimViewModel) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 20.dp)
    ) {
        var timeAText by remember { mutableStateOf("") }
        var timeBText by remember { mutableStateOf("") }
        val isAfternoon by viewModel.isAfternoon.collectAsState()

            Text(
                text = stringResource(id = R.string.moim_datetime_label2),
                style = TmTypo.current.Body2,
                color = TmtmColorPalette.current.color_text_body_quaternary
            )
            TmMarginVerticalSpacer(size = 8)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(modifier = Modifier
                    .width(88.dp)
                    .height(55.dp)
                    .background(color = TmtmColorPalette.current.elevation_color_elevation_level01)
                    .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = isAfternoon,
                            style = TmTypo.current.Body1,
                            color = TmtmColorPalette.current.color_text_body_primary
                        )
                        Icon(painter = painterResource(id = R.drawable.ic_arrow_down_l),
                            contentDescription = null,
                            tint = TmtmColorPalette.current.color_icon_level03,
                            modifier = Modifier
                            .size(16.dp)
                            .clickable { viewModel.updateBottomSheet(BottomSheet.Time) })
                    }
                }
                TmMarginHorizontalSpacer(size = 8)

                OutlinedTextField(
                    value = timeAText,
                    modifier = Modifier
                        .width(58.dp)
                        .wrapContentHeight(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    placeholder = {
                        Text(
                            text = "0시",
                            style = TmTypo.current.Body1,
                            color = TmtmColorPalette.current.color_text_body_quinary
                        )
                    },
                    onValueChange = { newText ->
                        timeAText = newText.take(2)
                        updateTimeIfComplete(timeAText, timeBText, viewModel)
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
                TmMarginHorizontalSpacer(size = 8)

                OutlinedTextField(
                    value = timeBText,
                    modifier = Modifier
                        .width(75.dp)
                        .wrapContentHeight(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    placeholder = {
                        Text(
                            text = "00분",
                            style = TmTypo.current.Body1,
                            color = TmtmColorPalette.current.color_text_body_quinary
                        )
                    },
                    onValueChange = { newText ->
                        timeBText = newText.take(2)
                        updateTimeIfComplete(timeAText, timeBText, viewModel)
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
    }
}

@Composable
fun MoimDateInputField(
    placeHolder:String,
    viewModel: MoimViewModel,
    isTimeField: Boolean = false
) {
    var text by remember { mutableStateOf("") }
    val time by viewModel.time.collectAsState()
    val date by viewModel.date.collectAsState()

    LaunchedEffect(isTimeField, time, date) {
        text = if (isTimeField) time else date
    }

    OutlinedTextField(
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        placeholder = { Text(text =placeHolder, style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary)},
        onValueChange = { newText ->
            text = newText
            if (newText.length == 4) {
                if (isTimeField) {
                    viewModel.updateTime2(newText)
                } else {
                    viewModel.updateDate2(newText)
                }
            }
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
