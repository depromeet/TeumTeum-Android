package com.teumteum.teumteum.presentation.moim

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.compose.theme.TmTypo
import com.teumteum.teumteum.presentation.compose.theme.TmtmColorPalette

@Composable
fun MoimCreateTopic() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .background(color = TmtmColorPalette.current.GreyWhite),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        CreateMoimTitle(string = "어떤 주제로 모임을 만들까요?")
        CreateTopicContent()
        Spacer(modifier = Modifier.weight(1f))
        TeumDivider()
        MoimCreateBtn(text = "다음")
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun TeumDivider() {
    Divider(
        color = TmtmColorPalette.current.ColorDivider,
        thickness = 1.dp,
        startIndent = 0.dp
    )
}


@Composable
fun MoimCreateBtn(text: String) {
    var isClicked: Boolean = false
    val buttonColors = if (isClicked) TmtmColorPalette.current.color_button_active else TmtmColorPalette.current.Gray200
    val textColors = if(isClicked) TmtmColorPalette.current.GreyWhite else TmtmColorPalette.current.Gray300
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        onClick = { !isClicked },
        colors = ButtonDefaults.buttonColors(containerColor = buttonColors),
        shape = RoundedCornerShape(size = 4.dp)
    ) {
        Text(
            text = text,
            style = TmTypo.current.HeadLine6,
            color = textColors
        )
    }
}

@Composable
fun CreateMoimTitle(string:String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(96.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = string, style= TmTypo.current.HeadLine3, color = TmtmColorPalette.current.Gray900,
            modifier = Modifier.padding(vertical = 32.dp))
    }
}

@Composable
fun CreateTopicContent() {
    val selectedTopicIndex = remember { mutableStateOf(-1) }
    LazyColumn {
        itemsIndexed(TopicType.values()) { index, topicType ->
            val isSelected = index == selectedTopicIndex.value
            CreateTopicItem(
                title = topicType.title,
                subTitle = topicType.subTitle,
                isSelected = isSelected,
                onItemSelected = { selectedTopicIndex.value = index }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CreateTopicItem(
    title:String,
    subTitle: String,
    isSelected: Boolean,
    onItemSelected: () -> Unit
) {
    val radioIcon = if (isSelected) R.drawable.ic_radio_active else R.drawable.ic_radio
    Box(
        modifier = Modifier
            .width(360.dp)
            .height(92.dp)
            .clickable(onClick = onItemSelected)
            .padding(horizontal = 20.dp)
            .background(color = TmtmColorPalette.current.Gray50, shape = RoundedCornerShape(4.dp))
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .width(240.dp)
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(text = title, style = TmTypo.current.HeadLine6, color = TmtmColorPalette.current.Gray900)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = subTitle, style = TmTypo.current.Body2, color = TmtmColorPalette.current.Gray550)
            }

            Image(painter = painterResource(id = radioIcon),
                contentDescription = "image description",
                modifier = Modifier
                    .padding(1.dp)
                    .width(24.dp)
                    .height(24.dp)
            )
        }
    }
}

enum class TopicType(val value: String, val title: String ,val subTitle: String) {
    SHARING_WORRIES("고민_나누기", "고민 나누기", "직무,커리어 고민을 나눠보세요"),
    STUDY("스터디", "스터디", "관심 분야 스터디로 목표를 달성해요"),
    GROUP_WORK("모여서_작업", "모여서 작업", "다같이 모여서 작업해요(모각코,모각일)"),
    SIDE_PROJECT("사이드_프로젝트", "사이드 프로젝트","사이드 프로젝트로 팀을 꾸리고 성장하세요")

}

@Preview
@Composable
fun previewtmtm1() {
    MoimCreateTopic()
}