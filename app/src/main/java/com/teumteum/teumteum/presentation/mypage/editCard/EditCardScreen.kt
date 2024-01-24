package com.teumteum.teumteum.presentation.mypage.editCard

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teumteum.base.component.compose.TeumDivider
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmInputField
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.EditCardViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SheetEvent

@Composable
fun EditCardScreen(
    MyPageViewModel: MyPageViewModel,
    viewModel: EditCardViewModel,
    navController: NavController
) {
    val activity = LocalContext.current as? MainActivity
    val name = viewModel.userName.collectAsState()
    val isNameValid by viewModel.isNameValid.collectAsState()
    val companyName by viewModel.companyName.collectAsState()
    val goal by viewModel.goalText.collectAsState()
    val jobClass by viewModel.jobClass.collectAsState()
    val community by viewModel.community.collectAsState()
    val jobDetailClass by viewModel.jobDetailClass.collectAsState()
    val mbti by viewModel.mbtiText.collectAsState()
    val date = viewModel.userBirth.collectAsState()
    val area by viewModel.preferredArea.collectAsState()
    val interests by viewModel.interestField.collectAsState()


    TmScaffold(
        topbarText = stringResource(id = R.string.setting_edit_card_topbar),
        onClick = {
            navController.popBackStack()
            activity?.showBottomNavi()
        }
    ){

        val scrollState = rememberScrollState()
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState)
            .background(color = TmtmColorPalette.current.color_background)
            .wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {
            TmMarginVerticalSpacer(size = 80)

            //이름
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label1))
            TmMarginVerticalSpacer(size = 8)
            TmInputField(
                text = R.string.setting_edit_card_placeholder1,
                text_error = R.string.setting_edit_card_error1,
                value = name.value,
                onValueChange = { updatedName ->
                    viewModel.updateUserName(updatedName)
                },
                isError = !isNameValid
            )

            //생년월일
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label2))
            TmMarginVerticalSpacer(size = 8)
            TmInputField(
                text = R.string.setting_edit_card_placeholder2,
                text_error = R.string.setting_edit_card_error2,
                value = date.value,
                onValueChange = {updatedDate ->
                    val formattedDate = viewModel.formatAsDateInput(updatedDate)
                    viewModel.updateUserBirth(formattedDate)
                },
                isError = !viewModel.isValidDate(date.value),
                isDate = true
            )

            //상태
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label3))
            TmMarginVerticalSpacer(size = 8)
            EditCardBottomBox(
                text = community,
                viewModel = viewModel,
                sheetEvent = SheetEvent.Status
            )

            //직장명
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label4))
            TmMarginVerticalSpacer(size = 8)
            TmInputField(
                value = companyName,
                text = R.string.setting_edit_card_placeholder4,
                text_error = R.string.setting_edit_card_error4,
                onValueChange = {
                    if (it.length <= 20) {
                        viewModel.updateCompanyName(it)
                    }
                },
                isError = companyName.length > 20
            )

            //직군
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label5))
            TmMarginVerticalSpacer(size = 8)
            EditCardBottomBox(
                text = jobClass,
                viewModel = viewModel,
                sheetEvent = SheetEvent.JobClass
            )

            //직무
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label6))
            TmMarginVerticalSpacer(size = 8)
            EditCardBottomBox(
                text = jobDetailClass,
                viewModel = viewModel,
                sheetEvent = SheetEvent.JobDetail
            )

            //MBTI
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label7))
            TmMarginVerticalSpacer(size = 8)
            EditCardBottomBox(
                text = mbti,
                viewModel = viewModel,
                sheetEvent = SheetEvent.Mbti
            )

            //관심 지역
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label8))
            TmMarginVerticalSpacer(size = 8)
            EditCardBottomBox(
                text = area,
                viewModel = viewModel,
                sheetEvent = SheetEvent.Area
            )

            //자기 개발
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label9))
            TmMarginVerticalSpacer(size = 8)
            InterestsChips(
                interests = interests,
                onClick = { viewModel.triggerSheetEvent(SheetEvent.SignUp)},
                viewModel = viewModel
            )
            TmMarginVerticalSpacer(size = 20)

            //틈틈 목표
            EditCardLabel(string = stringResource(id = R.string.setting_edit_card_label10))
            TmMarginVerticalSpacer(size = 8)
            TmInputField(text = R.string.setting_edit_card_placeholder10,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(134.dp),
                value = goal,
                onValueChange = {
                    if (it.length <= 50) { // 입력 길이가 50자 이하인 경우에만 업데이트
                        viewModel.updateGoalText(it)
                    } else {
                        val truncatedText = it.take(50)
                        viewModel.updateGoalText(truncatedText)
                    }
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            TeumDivider()
            EditCardBtn(text = stringResource(id = R.string.setting_edit_card_btn), isEnabled = name.value.isNotEmpty(), viewModel = viewModel)
            TmMarginVerticalSpacer(size = 14)
        }
    }
}

@Composable
fun EditInterestChip(

) {

}

@Composable
fun EditCardBottomBox(
    text: String,
    viewModel: EditCardViewModel,
    sheetEvent: SheetEvent
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
                viewModel.triggerSheetEvent(sheetEvent)
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
            androidx.compose.material3.Text(
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

@Composable
fun EditCardLabel(string: String) {
    androidx.compose.material3.Text(
        text = string,
        style = TmTypo.current.Body2,
        color = TmtmColorPalette.current.color_text_body_quaternary,
        modifier = Modifier.clickable {  }
    )
}


@Composable
fun EditCardBtn(
    text: String,
    viewModel: EditCardViewModel,
    isEnabled: Boolean = false,
) {
    // 각 필드의 현재 상태를 수집
    val name = viewModel.userName.collectAsState().value
    val companyName = viewModel.companyName.collectAsState().value
    val goal = viewModel.goalText.collectAsState().value
    val jobClass = viewModel.jobClass.collectAsState().value
    val community = viewModel.community.collectAsState().value
    val jobDetailClass = viewModel.jobDetailClass.collectAsState().value
    val mbti = viewModel.mbtiText.collectAsState().value
    val date = viewModel.userBirth.collectAsState().value
    val area = viewModel.preferredArea.collectAsState().value
    val interests = viewModel.interestField.collectAsState().value

    // 모든 필드가 유효한지 검사
    val isAllValid = name.isNotEmpty() && companyName.isNotEmpty() && goal.length <=50 &&
            jobClass.isNotEmpty() && community.isNotEmpty() && jobDetailClass.isNotEmpty() &&
            mbti.isNotEmpty() && date.isNotEmpty() && area.isNotEmpty() &&
            interests.isNotEmpty() && viewModel.isNameValid.collectAsState().value &&
            viewModel.isValidDate(date)

    // 버튼의 활성화 상태

    val buttonColors =
        if (isAllValid) TmtmColorPalette.current.color_button_active else TmtmColorPalette.current.color_button_disabled
    val textColors =
        if (isAllValid) TmtmColorPalette.current.GreyWhite else TmtmColorPalette.current.color_text_button_primary_disabled
    androidx.compose.material3.Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .padding(vertical = 10.dp),
        enabled = isEnabled,
        onClick = {
            if(isAllValid) { viewModel.updateUserInfo() }
            },
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