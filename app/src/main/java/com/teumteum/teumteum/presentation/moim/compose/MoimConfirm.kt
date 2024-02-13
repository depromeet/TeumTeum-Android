package com.teumteum.teumteum.presentation.moim.compose

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.compose.TeumDivider
import com.teumteum.base.component.compose.TeumDividerHorizontalThick
import com.teumteum.base.component.compose.TeumDividerThick
import com.teumteum.base.component.compose.TmDialog
import com.teumteum.base.component.compose.TmIndicator
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.domain.entity.Friend
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.group.join.JoinFriendListActivity
import com.teumteum.teumteum.presentation.group.join.check.GroupMeetCheckActivity
import com.teumteum.teumteum.presentation.moim.MoimFragmentDirections
import com.teumteum.teumteum.presentation.moim.MoimModifyFragmentDirections
import com.teumteum.teumteum.presentation.moim.MoimViewModel
import com.teumteum.teumteum.presentation.moim.ScreenState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MoimConfirm(
    viewModel: MoimViewModel,
    navController: NavController? = null,
    activity: Activity,
    isJoinView: Boolean,
    meetingId: Long? = null,
    onClick: ()-> Unit = {
        activity.finish()
        (activity as? BindingActivity<*>)?.closeActivitySlideAnimation()
    },

    ) {
    meetingId?.let {
        viewModel.setMeetingId(it)
    }

    val showDialog = remember { mutableStateOf(false) }
    val screenState by viewModel.screenState.collectAsState()
    val isJoined by viewModel.isUserJoined.collectAsState()
    val isHost by viewModel.isUserHost.collectAsState()

    LaunchedEffect(key1 = screenState) {
        showDialog.value = screenState == ScreenState.Cancel || screenState == ScreenState.Delete || screenState==ScreenState.Report
    }

    if (showDialog.value) {
        when (screenState) {
            ScreenState.Cancel -> {
                TmDialog(
                    title = stringResource(id = R.string.setting_dialog_cancel),
                    okText = stringResource(id = R.string.setting_dialog_cancel_btn2),
                    cancelText = stringResource(id = R.string.setting_dialog_cancel_btn1),
                    onOk = {
                        if (meetingId != null) {
                            viewModel.cancelMeeting(meetingId)
                        }
                        showDialog.value = false
                    },
                    onCancel = {
                        showDialog.value = false
                        viewModel.updateSheetEvent(ScreenState.CancelInit)
                    },
                    onDismiss = {
                        showDialog.value = false
                        viewModel.updateSheetEvent(ScreenState.CancelInit)
                    }
                )
            }
            ScreenState.Report -> {
                TmDialog(
                    title = stringResource(id = R.string.setting_dialog_report),
                    okText = stringResource(id = R.string.setting_dialog_report_btn2),
                    cancelText = stringResource(id = R.string.setting_dialog_report_btn1),
                    onOk = {
                        if (meetingId != null) {
                            viewModel.reportMeeting(meetingId)
                        }
                        showDialog.value = false
                    },
                    onCancel = {
                        showDialog.value = false
                        viewModel.updateSheetEvent(ScreenState.ReportInit)
                    },
                    onDismiss = {
                        showDialog.value = false
                        viewModel.updateSheetEvent(ScreenState.ReportInit)
                    }
                )
            }
            ScreenState.Delete -> {
                TmDialog(
                    title = stringResource(id = R.string.setting_dialog_delete),
                    okText = stringResource(id = R.string.setting_dialog_delete_btn2),
                    cancelText = stringResource(id = R.string.setting_dialog_delete_btn1),
                    onOk = {
                        if (meetingId != null) {
                            viewModel.deleteMeeting(meetingId)
                        }
                        showDialog.value = false
                    },
                    onCancel = {
                        showDialog.value = false
                        viewModel.updateSheetEvent(ScreenState.DeleteInit)
                    },
                    onDismiss = {
                        showDialog.value = false
                        viewModel.updateSheetEvent(ScreenState.DeleteInit)
                    }
                )
            }
            else -> {}
        }
    }

    TmScaffold(
        topbarText =
        if (isJoinView) ""
        else if (meetingId != null && meetingId > 0) ""
        else stringResource(id = R.string.moim_confirm_appbar),
        onClick = {
            if(isJoinView) { onClick() }
            else { viewModel.goPreviousScreen() }
            },
        onConfirmClick = {
            Log.d("screenState", screenState.toString())
            viewModel.updateSheetEvent(ScreenState.Report) },
        isConfirm = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.color_background)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                    item {
                        MoimPhotoPager(viewModel)
                        MoimConfirmInfo(viewModel, isJoinView =  isJoinView)
                        TmMarginVerticalSpacer(size = 32)
                        TeumDividerThick(int = 8)
                        TmMarginVerticalSpacer(size = 20)
                        MoimHostRow(viewModel)
                        TmMarginVerticalSpacer(size = 20)
                        if (isJoinView) {
                            TeumDividerHorizontalThick(int = 1, 20)
                            TmMarginVerticalSpacer(size = 20)
                            MoimJoinUserRow(viewModel) {
                                activity.startActivity(JoinFriendListActivity.getIntent(activity, Json.encodeToString(it)))
                                (activity as? BindingActivity<*>)?.openActivitySlideAnimation()
                            }
                            TmMarginVerticalSpacer(size = 20)
                        }
                        TeumDividerThick(int = 8)
                        MoimConfirmIntroColumn(viewModel)
                    }
                }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                if (isJoinView) {
                    if (meetingId != null && meetingId > 0) {
                        //meeting Id가 argument로 있는데, 참여중인 경우
                        if(isJoined) {
                            if(!isHost) {
                                TeumDivider()
                                MoimCancelBtn(
                                    viewModel = viewModel
                                ) {
                                    if (meetingId != null) { viewModel.cancelMeeting(it) }
                                    else { viewModel.updateSheetEvent(ScreenState.Failure) }
                                }
                                TmMarginVerticalSpacer(size = 24)
                            } else {
                                TeumDivider()
                                MoimHostBtn(
                                    viewModel = viewModel,
                                    navController = navController
                                ) {
                                    if(meetingId != null) {viewModel.deleteMeeting(it)}
                                }
                            }
                        }
                        else if(isHost) {
                            TeumDivider()
                            MoimHostBtn(
                                viewModel = viewModel,
                                navController = navController
                            ) {
                                if(meetingId != null) {viewModel.deleteMeeting(it)}
                            }
                        }
                        //meeting Id가 argument로 있는데, 참여 중이지 않은 경우
                        else {
                            TeumDivider()
                            MoimJoinBtn(viewModel = viewModel) {
                                activity.startActivity(
                                    GroupMeetCheckActivity.getIntent(activity, it)
                                )
                            }
                            TmMarginVerticalSpacer(size = 24)
                        }
                    } else {
                        //meeting Id가 argument로 없음, 참여중이지 않은경우(moimCreate)
                        if(isJoined == false) {
                            TeumDivider()
                            MoimJoinBtn(viewModel = viewModel) {
                                activity.startActivity(
                                    GroupMeetCheckActivity.getIntent(activity, it)
                                )
                            }
                            TmMarginVerticalSpacer(size = 24)
                        }
                    }
                } else {
                    if (meetingId != null && meetingId > 0) {
                            TeumDivider()
                            MoimJoinBtn(viewModel = viewModel) {
                                activity.startActivity(
                                    GroupMeetCheckActivity.getIntent(activity, it)
                                )
                            }
                            TmMarginVerticalSpacer(size = 24)
                    } else {
                        TeumDivider()
                        MoimCreateBtn(text = stringResource(id = R.string.moim_confirm_btn), viewModel = viewModel)
                        TmMarginVerticalSpacer(size = 24)
                    }
                }
            }
        }
    }
    BackHandler {
        if(isJoinView) {
            activity.finish()
            (activity as? BindingActivity<*>)?.closeActivitySlideAnimation()
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
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 276.dp)
                    .clipToBounds(),
                contentScale = ContentScale.FillWidth
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
fun MoimConfirmInfo(
    viewModel: MoimViewModel,
    isJoinView: Boolean
) {
    val title by viewModel.title.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = TmtmColorPalette.current.color_background)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TmMarginVerticalSpacer(size = 32)
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
            MoimInfoCard(viewModel, isJoinView = isJoinView)
        }

}

@Composable
fun MoimInfoCard(viewModel: MoimViewModel,isJoinView: Boolean) {
    val topic by viewModel.topic.collectAsState()
    val people by viewModel.people.collectAsState()
    val date by viewModel.date.collectAsState()
    val time by viewModel.time.collectAsState()
    val isAfternoon by viewModel.isAfternoon.collectAsState()
    val detailAddress by viewModel.detailAddress.collectAsState()

    val dateString = if (isJoinView) {
        if(date.length ==4) {
            "${date.substring(0, 2)}월 ${date.substring(2, 4)}일 $time"
        } else {
            "$date $time"
        }
    } else {
        if (date.length == 8) {
            "${date.substring(0, 4)}년 ${date.substring(4, 6)}월 ${date.substring(6, 8)}일 $isAfternoon $time"
        } else {
            date
        }
    }

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
            MoimCardRow(title = stringResource(id = R.string.moim_confirm_title1), text = dateString)
            TmMarginVerticalSpacer(size = 4)
            MoimCardRow(title = stringResource(id = R.string.moim_confirm_title2), text = viewModel.getTopicTitle(topic))
            TmMarginVerticalSpacer(size = 4)
            MoimCardRow(title = stringResource(id = R.string.moim_confirm_title3), text = "${people}명")
            TmMarginVerticalSpacer(size = 4)
            MoimCardRow(title = stringResource(id = R.string.moim_confirm_title4), text = detailAddress)

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
fun MoimHostRow(viewModel: MoimViewModel) {
    val characterId by viewModel.moinCreateUserCharacterId.collectAsState()
    val name by viewModel.moinCreateUserName.collectAsState()
    val job by viewModel.moinCreateUserJob.collectAsState()
    Row(
        modifier = Modifier
            .width(147.dp)
            .height(40.dp)
            .padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(color = TmtmColorPalette.current.elevation_color_elevation_level01)
        ) {
            Image(painter = painterResource(id = characterId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }

        Column(
            modifier= Modifier
                .wrapContentSize()
                .padding(start = 12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = name,
                style = TmTypo.current.HeadLine6,
                color = TmtmColorPalette.current.color_text_headline_primary
            )
            Text(
                text = job,
                style = TmTypo.current.Caption1,
                color = TmtmColorPalette.current.color_text_headline_primary,
                modifier = Modifier.padding(start = 1.dp)
            )
        }
    }
}

@Composable
fun MoimJoinUserRow(viewModel: MoimViewModel, onJoinListClick: (List<Friend>) -> (Unit)) {
    val moimJoinUserList by viewModel.moimJoinUsers.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onJoinListClick(moimJoinUserList) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            MoimJoinList(moimJoinUserList, viewModel.characterList)
        }
        Box(
            modifier = Modifier
                .padding(end = 20.dp)
        ) {
            Text(text = "현재 참여중인 사람 ${moimJoinUserList.size}명",
                style = TmTypo.current.Body2,
                color = TmtmColorPalette.current.color_text_body_teritary)
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

@Composable
fun MoimJoinList(moimJoinUserList: List<Friend>, characterList: HashMap<Int, Int>) {
    LazyRow {
        itemsIndexed(moimJoinUserList) { index, item ->
            MoimJoinListItem(index, item, characterList)
        }
    }
}

@Composable
fun MoimJoinListItem(index: Int, item: Friend, characterList: HashMap<Int, Int>) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .aspectRatio(1f)
            .offset(
                x = if (index > 0) (-8).dp * index else 0.dp
            )
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(color = TmtmColorPalette.current.elevation_color_elevation_level01)
        )

        Image(
            painter = painterResource(
                id = characterList[item.characterId] ?: R.drawable.ic_penguin
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MoimHostBtn(
    viewModel: MoimViewModel,
    navController: NavController?,
    onJoinGroupClick: (Long) -> Unit
) {
    val meetingId by viewModel.meetingsId.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        androidx.compose.material3.Button(
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            onClick = {
                viewModel.updateSheetEvent(ScreenState.Delete)
            },
            colors = ButtonDefaults.buttonColors(containerColor = TmtmColorPalette.current.color_button_alternative),
            shape = RoundedCornerShape(size = 4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.setting_dialog_delete_row1),
                style = TmTypo.current.HeadLine6,
                color = TmtmColorPalette.current.color_text_button_alternative
            )
        }

        androidx.compose.material3.Button(
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            onClick = {
                if (navController != null) {
                    val action = meetingId?.let { MoimFragmentDirections.actionFragmentMoimToFragmentModifyMoim(meetingId) }
                    if (action != null) { navController.navigate(action) }
                } else {
                    viewModel.updateSheetEvent(ScreenState.Modify)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = TmtmColorPalette.current.color_button_alternative),
            shape = RoundedCornerShape(size = 4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.setting_dialog_delete_row2),
                style = TmTypo.current.HeadLine6,
                color = TmtmColorPalette.current.color_text_button_alternative
            )
        }
    }
}

@Composable
fun MoimCancelBtn(
    viewModel: MoimViewModel,
    onJoinGroupClick: (Long) -> Unit,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
        )
    {
        val isBookMark by viewModel.isBookmark.collectAsState()
        val bookMarkIcon = if(isBookMark) R.drawable.ic_heart_fill else R.drawable.ic_heart_default
        val meetingId by viewModel.meetingsId.collectAsState()

        Icon(
            painterResource(id = bookMarkIcon),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.clickable {
                if(isBookMark) viewModel.deleteBookmark(meetingId)
                else viewModel.saveBookmark(meetingId)
            }
        )
        androidx.compose.material3.Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            onClick = {
                viewModel.updateSheetEvent(ScreenState.Cancel)
            },
            colors = ButtonDefaults.buttonColors(containerColor = TmtmColorPalette.current.color_button_alternative),
            shape = RoundedCornerShape(size = 4.dp)
        ) {
            Text(
                text = "참여 안할래요",
                style = TmTypo.current.HeadLine6,
                color = TmtmColorPalette.current.color_text_button_alternative
            )
        }

    }
}


@Composable
fun MoimJoinBtn(
    viewModel: MoimViewModel,
    onJoinGroupClick: (Long) -> Unit,
) {
    val people by viewModel.people.collectAsState()
    val moimJoinUsers by viewModel.moimJoinUsers.collectAsState()
    val meetingsId by viewModel.meetingsId.collectAsState()
    val isBookMark by viewModel.isBookmark.collectAsState()
    val bookMarkIcon = if(isBookMark) R.drawable.ic_heart_fill else R.drawable.ic_heart_default

    Row(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Icon(
            painterResource(id = bookMarkIcon),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.clickable {
                if(isBookMark) viewModel.deleteBookmark(meetingsId)
                else viewModel.saveBookmark(meetingsId)
            }
        )
        androidx.compose.material3.Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            onClick = {
                onJoinGroupClick(meetingsId)
            },
            colors = ButtonDefaults.buttonColors(containerColor = TmtmColorPalette.current.color_button_active),
            shape = RoundedCornerShape(size = 4.dp)
        ) {
            Text(
                text = "참여할래요 (${moimJoinUsers.size}/${people})",
                style = TmTypo.current.HeadLine6,
                color = TmtmColorPalette.current.color_text_button_primary_default
            )
        }
    }
}


