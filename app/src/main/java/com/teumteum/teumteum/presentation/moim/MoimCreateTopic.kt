package com.teumteum.teumteum.presentation.moim

import android.util.Log
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.teumteum.base.component.compose.TeumDivider
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.teumteum.R
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette

@Composable
fun MoimCreateTopic(viewModel: MoimViewModel, onClick: ()->Unit) {
    val topicIndex = remember { mutableStateOf(-1) }
    TmScaffold(onClick = { onClick() }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            TmMarginVerticalSpacer(size = 48)
            CreateMoimTitle(string = stringResource(id = R.string.moim_topic_title))
            CreateTopicContent(viewModel, topicIndex)
            Spacer(modifier = Modifier.weight(1f))
            TeumDivider()
            MoimCreateBtn(text = stringResource(id = R.string.moim_next_btn), isEnabled = topicIndex.value >=0, viewModel = viewModel)
            TmMarginVerticalSpacer(size = 24)
        }
    }

}

@Composable
fun MoimCreateBtn(
    text: String,
    viewModel: MoimViewModel ,
    isEnabled: Boolean = true,
    navController: NavController? = null
) {
    val screenState by viewModel.screenState.collectAsState()
    val context = LocalContext.current

    val buttonColors = if (isEnabled) TmtmColorPalette.current.color_button_active else TmtmColorPalette.current.color_button_disabled
    val textColors = if(isEnabled) TmtmColorPalette.current.color_text_button_primary_default else TmtmColorPalette.current.color_text_button_primary_disabled
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        enabled = isEnabled,
        onClick = {
            if (screenState == ScreenState.Create) {
                viewModel.createMoim()
                Log.d("screenState", screenState.toString())
            } else if (screenState == ScreenState.Finish) {
                navController?.navigate(R.id.fragment_home)
            }
            else {
            viewModel.goToNextScreen() }},
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
fun CreateTopicContent(viewModel: MoimViewModel, topicIndex: MutableState<Int>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        itemsIndexed(TopicType.values()) { index, topicType ->
            val isSelected = index == topicIndex.value
            CreateTopicItem(
                title = topicType.title,
                subTitle = topicType.subTitle,
                isSelected = isSelected,
                onItemSelected = {
                    topicIndex.value = index
                    viewModel.updateTopic(topicType)
                    Log.d("moim_topic", topicType.toString())
                }
            )
            TmMarginVerticalSpacer(size = 12)
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
            .fillMaxWidth()
            .height(92.dp)
            .clickable(onClick = onItemSelected)
            .padding(horizontal = 20.dp)
            .background(color = TmtmColorPalette.current.elevation_color_elevation_level01, shape = RoundedCornerShape(4.dp))
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
                TmMarginVerticalSpacer(size = 8)
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