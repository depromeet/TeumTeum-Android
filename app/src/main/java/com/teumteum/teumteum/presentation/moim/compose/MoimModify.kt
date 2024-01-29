package com.teumteum.teumteum.presentation.moim.compose

import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.teumteum.base.component.compose.TeumDivider
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmInputField
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.moim.BottomSheet
import com.teumteum.teumteum.presentation.moim.MoimViewModel
import com.teumteum.teumteum.presentation.mypage.editCard.EditCardLabel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.EditCardViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SheetEvent


@Composable
fun MoimModify(viewModel: MoimViewModel, navController: NavController) {
    TmScaffold(
        topbarText = stringResource(id = R.string.modify_topbar),
        onClick = { navController.popBackStack() }
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
            TmMarginVerticalSpacer(size = 80)
            MoimModifyColumn(viewModel, navController)
            Spacer(modifier = Modifier.weight(1f))
            TeumDivider()
            TmMarginVerticalSpacer(size = 24)
        }

    }
}

@Composable
fun MoimModifyColumn(viewModel: MoimViewModel, navController: NavController) {
    val title by viewModel.title.collectAsState()
    val introduction by viewModel.introduction.collectAsState()
    val address by viewModel.address.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = TmtmColorPalette.current.color_background)
        .padding(horizontal = 20.dp)) {

        //모임 주제
        EditCardLabel(string = stringResource(id = R.string.modify_title1))
        TmMarginVerticalSpacer(size = 8)
        EditMeetingBottomBox(
            text = stringResource(R.string.modify_placeholder1),
            viewModel = viewModel,
            bottomSheet = BottomSheet.Topic
        )

        //모임 이름
        EditCardLabel(string = stringResource(R.string.modify_title2))
        TmMarginVerticalSpacer(size = 8)
        TmInputField(
            text = R.string.modify_title2,
            text_error = R.string.modify_placeholder2,
            value = title,
            onValueChange = { newTitle ->
                viewModel.updateTitle(newTitle)
            },
            isError = title.isBlank(),
        )

        //모임 소개
        EditCardLabel(string = stringResource(R.string.modify_title3))
        TmMarginVerticalSpacer(size = 8)
        TmInputField(
            text = R.string.modify_title3,
            text_error = R.string.modify_placeholder3,
            value = introduction,
            onValueChange = { newText ->
                viewModel.updateIntroduce(newText)
            },
            modifier = Modifier
                .height(272.dp),
            isError = introduction.isBlank(),
        )
        ModifySystemText(text = R.string.setting_edit_card_error10)
        TmMarginVerticalSpacer(size = 20)

        //모임 사진
        EditCardLabel(string = stringResource(R.string.modify_title4))
        TmMarginVerticalSpacer(size = 8)
        ModifyPhotoColumn(viewModel = viewModel)
        TmMarginVerticalSpacer(size = 8)
        ModifySystemText(text = R.string.moim_introduce_system_photo2)
        TmMarginVerticalSpacer(size = 20)


        //모임 날짜
        EditCardLabel(string = stringResource(R.string.modify_title5))
        TmMarginVerticalSpacer(size = 8)
        MoimDateInputField(placeHolder = stringResource(R.string.modify_title5), viewModel = viewModel)
        TmMarginVerticalSpacer(size = 20)


        //모임 시간
        EditCardLabel(string = stringResource(R.string.modify_title6))
        TmMarginVerticalSpacer(size = 8)
        MoimDateInputField(
            placeHolder = stringResource(id = R.string.modify_placeholder6),
            viewModel = viewModel,
            isTimeField = true
        )
        TmMarginVerticalSpacer(size = 20)


        //모임 도로명 주소
        EditCardLabel(string = stringResource(R.string.modify_title7))
        TmMarginVerticalSpacer(size = 8)
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = TmtmColorPalette.current.elevation_color_elevation_level01,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                navController.navigate(R.id.action_fragment_modify_moim_to_fragment_web_view)
            }
        ) {
            Text(
                text = address ?: stringResource(id = R.string.moim_address_placeholdler1),
                color = TmtmColorPalette.current.color_text_body_quinary,
                style = TmTypo.current.Body1,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
        }
        TmMarginVerticalSpacer(size = 20)


        //모임 상세 주소
        EditCardLabel(string = stringResource(R.string.modify_title8))
        TmMarginVerticalSpacer(size = 8)
        MoimAddressInputField(viewModel = viewModel, placeHolder = stringResource(id = R.string.moim_address_placeholder2))
        TmMarginVerticalSpacer(size = 20)


        //모임 참여 인원
        EditCardLabel(string = stringResource(R.string.modify_title9))
        TmMarginVerticalSpacer(size = 8)
        EditMeetingBottomBox(
            text = stringResource(R.string.modify_placeholder9),
            viewModel = viewModel,
            bottomSheet = BottomSheet.People
        )
        TmMarginVerticalSpacer(size = 20)


    }
}

@Composable
fun ModifySystemText(@StringRes text: Int) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(color = TmtmColorPalette.current.color_background)
        .wrapContentHeight(),
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
                .fillMaxWidth()
        )

    }
}

@Composable
fun ModifyPhotoColumn(
    viewModel: MoimViewModel
) {
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
        TmMarginVerticalSpacer(size = 8)
        ModifyPhotoInput(imageUri, {launcher.launch("image/*")}, viewModel::removeImage)
    }
}

@Composable
fun ModifyPhotoInput(
    attachedImages: List<Uri>,
    onAddImage: () -> Unit,
    onRemoveImage : (Uri)-> Unit = {}
) {
    LazyRow() {
        items(attachedImages) { imageUri ->
            Box(
                modifier = Modifier
                    .size(72.dp, 73.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(4.dp)),
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = imageUri,
                        builder = {
                            scale(Scale.FILL)
                        }
                    )
                    ,
                    contentDescription = "Attached Image",
                    modifier = Modifier
                        .size(72.dp, 73.dp)
                        .padding(4.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { onRemoveImage(imageUri) },
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close_fill),
                        contentDescription = "Remove Image",
                        tint = Color.Unspecified
                    )
                }
            }
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