package com.teumteum.teumteum.presentation.moim

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.teumteum.base.component.compose.TeumDivider
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.teumteum.R
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.TmSnackBar
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import kotlinx.coroutines.delay

@Composable
fun MoimIntroduce(
    viewModel: MoimViewModel,
    onClick: () -> Unit
) {
    val introduce by viewModel.introduction.collectAsState()
    val photo by viewModel.imageUri.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarEvent.collect { event ->
            when (event) {
                MoimViewModel.SnackbarEvent.FILE_OVER_10MB -> {
                    val result = snackbarHostState.showSnackbar(
                        message = "10mb 이하의 사진을 등록해 주세요",
                        duration = SnackbarDuration.Short
                    )
                    delay(1000)
                    snackbarHostState.currentSnackbarData?.dismiss()
                    if (result == SnackbarResult.Dismissed) {
                        viewModel.resetSnackbarEvent()
                    }
                }
                MoimViewModel.SnackbarEvent.DEFAULT -> {}
            }
        }
    }

    TmSnackBar(snackbarHostState = snackbarHostState)

    TmScaffold(onClick = {onClick()}) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            TmMarginVerticalSpacer(size = 48)
            CreateMoimTitle(string= stringResource(id = R.string.moim_introduce_title))
            TmMarginVerticalSpacer(size = 28)
            MoimIntroColumn(viewModel)
            TmMarginVerticalSpacer(size = 8)
            MoimSystemText(text =  R.string.moim_introduce_system_text)
            TmMarginVerticalSpacer(size = 20)
            TeumDivider()
            TmMarginVerticalSpacer(size = 20)

            MoimPhotoColumn(viewModel)
            TmMarginVerticalSpacer(size = 20)
            TmSnackBar(snackbarHostState = snackbarHostState)
            Spacer(modifier = Modifier.weight(1f))
            TeumDivider()
            MoimCreateBtn(text = stringResource(id = R.string.moim_next_btn), viewModel = viewModel, isEnabled = photo.isNotEmpty() && introduce.length in 10..200)
            TmMarginVerticalSpacer(size = 24)
        }
    }
}

@Composable
fun MoimIntroColumn(viewModel: MoimViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = TmtmColorPalette.current.color_background)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        MoimInputField(viewModel)
    }
}

@Composable
fun MoimSystemText(@StringRes text: Int) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(color = TmtmColorPalette.current.color_background)
        .wrapContentHeight()
        .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_system),
            contentDescription =null,
            modifier = Modifier.padding(vertical =0.dp)
        )
        TmMarginHorizontalSpacer(size = 6)
        Text(
            text = stringResource(id = text),
            style= TmTypo.current.Body3,
            color = TmtmColorPalette.current.color_text_caption_teritary_default,
            modifier = Modifier
                .width(284.dp)
        )

    }
}

@Composable
fun MoimInputField(viewModel: MoimViewModel) {
    val text by viewModel.introduction.collectAsState()

    OutlinedTextField(
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable { }
            .height(272.dp),
        placeholder = { Text(text = stringResource(id = R.string.moim_introduce_title), style= TmTypo.current.Body1, color = TmtmColorPalette.current.color_text_body_quinary) },
        onValueChange = { newText-> viewModel.updateIntroduce(newText) },
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

@Composable
fun MoimPhotoColumn(viewModel: MoimViewModel) {
    val imageUri by viewModel.imageUri.collectAsState()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri> ->
            viewModel.addImages(uris, context)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = TmtmColorPalette.current.GreyWhite),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.moim_introduce_title1_photo), style= TmTypo.current.SubTitle1, color = TmtmColorPalette.current.color_text_body_primary)
            TmMarginHorizontalSpacer(size = 6)
            Text(text = stringResource(id = R.string.moim_introduce_title2_photo), style= TmTypo.current.Body2, color= TmtmColorPalette.current.color_text_headline_teritary)
        }
        TmMarginVerticalSpacer(size = 8)
        MoimPhotoInput(imageUri) { launcher.launch("image/*") }
        TmMarginVerticalSpacer(size = 8)
        MoimSystemText(text = R.string.moim_introduce_system_photo)
    }
}

@Composable
fun MoimPhotoInput(
    attachedImages: List<Uri>,
    onAddImage: () -> Unit,
) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        items(attachedImages) { imageUri ->
            Image(
                painter = rememberImagePainter(
                    data = imageUri,
                    builder = {
                        scale(Scale.FILL)
                    }
                ),
                contentDescription = "Attached Image",
                modifier = Modifier
                    .size(72.dp, 73.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
        }
        if (attachedImages.size < 5) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_input_photo),
                    contentDescription = "Add Image",
                    modifier = Modifier
                        .size(72.dp, 73.dp)
                        .padding(4.dp)
                        .clickable { onAddImage() }
                )
            }
        }
    }
}




