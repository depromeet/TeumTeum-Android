package com.teumteum.teumteum.presentation.mypage.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teumteum.base.component.compose.TmDialog
import com.teumteum.base.component.compose.TmMarginHorizontalSpacer
import com.teumteum.base.component.compose.TmMarginVerticalSpacer
import com.teumteum.base.component.compose.TmScaffold
import com.teumteum.teumteum.R
import com.teumteum.base.component.compose.theme.TmTypo
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.BuildConfig
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.DialogEvent
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.getMemberSetting


@Composable
fun SettingScreen(viewModel: SettingViewModel, navController: NavController) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val dialogTitle = remember { mutableStateOf(context.getString(R.string.setting_dialog_default)) }
    val okText = remember { mutableStateOf(context.getString(android.R.string.ok)) }
    val cancelText = remember { mutableStateOf(context.getString(android.R.string.cancel)) }
    val currentEvent = remember { mutableStateOf(DialogEvent.DEFAULT) }
    val activity = LocalContext.current as? MainActivity

    LaunchedEffect(viewModel.dialogEvent) {
        viewModel.dialogEvent.collect { event ->
            when(event) {
                DialogEvent.LOGOUT -> {
                    showDialog.value = true
                    dialogTitle.value = context.getString(event.getTitleResId())
                    okText.value = context.getString(event.getOkTextResId())
                    cancelText.value = context.getString(event.getCancelTextResId())
                    currentEvent.value = event
                }
                else -> showDialog.value = false
            }
        }
    }

    if (showDialog.value) {
        TmDialog(
            title = dialogTitle.value,
            okText = okText.value,
            cancelText = cancelText.value,
            onOk = {
                viewModel.confirmDialogEvent(currentEvent.value)
                showDialog.value = false
            },
            onCancel = {
                showDialog.value = false
                viewModel.resetDialogEvent()
            },
            onDismiss = {
                showDialog.value = false
                viewModel.resetDialogEvent()
            }
        )
    }

    TmScaffold(
        topbarText = stringResource(id = R.string.setting_topbar),
        onClick = {
            navController.popBackStack()
            activity?.showBottomNavi()
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = TmtmColorPalette.current.elevation_color_elevation_level01)
        ) {
            val versionName = BuildConfig.VERSION_NAME
            TmMarginVerticalSpacer(size = 60)
            SettingAccountRow(viewModel, navController)
            TmMarginVerticalSpacer(size = 8)
            SettingToggle(title = "푸시알림", viewModel = viewModel)
            SettingColumn2(viewModel, navController)
            TmMarginVerticalSpacer(size = 14)
            Text(
                text = stringResource(id = R.string.setting_version_text) + " ${versionName}",
                style = TmTypo.current.Body2,
                color= TmtmColorPalette.current.color_text_body_quinary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

    }
}

@Composable
fun SettingAccountRow(viewModel: SettingViewModel, navController: NavController) {
    val name by viewModel.userName.collectAsState()
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(color = TmtmColorPalette.current.color_background)
        .padding(horizontal = 20.dp)
        .height(70.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = name, style = TmTypo.current.HeadLine6, color= TmtmColorPalette.current.color_text_headline_primary)
            TmMarginHorizontalSpacer(size = 4)
            Text(text = stringResource(id = R.string.setting_my_info_edit_text),
                style = TmTypo.current.Body3,
                color= TmtmColorPalette.current.color_text_body_teritary,
                modifier = Modifier.clickable { navController.navigate(R.id.fragment_edit_myinfo) }
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right_l ),
            contentDescription = "right_arrow", tint= Color.Unspecified,
            modifier = Modifier
                .size(20.dp)
                .clickable { navController.navigate(R.id.fragment_edit_myinfo) }
        )

    }
}

@Composable
fun SettingColumn2(viewModel: SettingViewModel, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = TmtmColorPalette.current.elevation_color_elevation_level01),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        items(getMemberSetting(viewModel, navController)) { item->
            SettingTitle(
                title = item.title,
                onClick = {
                    item.onClick()
                }
            )
        }
    }
}

@Composable
fun SettingTitle(title: String, onClick: ()-> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .background(color = TmtmColorPalette.current.color_background)
        .height(48.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = title, style = TmTypo.current.HeadLine7, color= TmtmColorPalette.current.color_text_body_primary)
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right_l ),
                contentDescription = "right_arrow", tint= Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
