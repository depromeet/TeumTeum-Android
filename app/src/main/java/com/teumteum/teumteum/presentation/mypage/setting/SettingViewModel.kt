package com.teumteum.teumteum.presentation.mypage.setting

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.mypage.SettingUiItem
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(): ViewModel() {

    private val _alarmState = MutableStateFlow(false)
    val alarmState: StateFlow<Boolean> = _alarmState.asStateFlow()
    fun onToggleChange(newToggleState: Boolean) {
        _alarmState.value = newToggleState
    }

    private val _settingStatus = MutableStateFlow(SettingStatus.DEFAULT)
    val settingStatus: StateFlow<SettingStatus> = _settingStatus

    fun updateSettingStatus(settingStatus: SettingStatus) {
        _settingStatus.value = settingStatus
    }

    private val _dialogEvent = MutableSharedFlow<DialogEvent>()
    val dialogEvent : SharedFlow<DialogEvent> = _dialogEvent.asSharedFlow()

    fun resetDialogEvent() {
        viewModelScope.launch {
            _dialogEvent.emit(DialogEvent.DEFAULT)
        }
    }

    private fun logout() {

    }

    private fun cancelMeeting() {

    }

    fun confirmDialogEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.LOGOUT -> {
                logout()
            }
            DialogEvent.CANCEL -> {
                cancelMeeting()
            }
            else -> {}
        }
    }
}

fun getMemberSetting(viewModel: SettingViewModel): List<SettingUiItem> {
    return listOf(
        SettingUiItem(title = "약관 및 개인정보 처리 동의", onClick = { viewModel.updateSettingStatus(SettingStatus.NOTION) }),
        SettingUiItem(title = "탈퇴하기", onClick = { viewModel.updateSettingStatus(SettingStatus.SIGNOUT) }),
        SettingUiItem(title = "로그아웃", onClick = { viewModel.updateSettingStatus(SettingStatus.LOGOUT) })
    )
}

fun getServiceGuide(): List<SettingUiItem> {
    return listOf(
        SettingUiItem(title = "서비스 이용약관", url = "https://sheer-billboard-d63.notion.site/KUSITMS-9e6619383bcd4ce68b6ba4b2b6ef0d40?pvs=4"),
        SettingUiItem(title = "개인정보 처리방침", url = "https://sheer-billboard-d63.notion.site/24a4639559d4433cb89c8f1abb889726?pvs=4")
    )
}


enum class SettingStatus { LOGOUT, SIGNOUT, SIGNOUTCONFIRM,  DEFAULT, NOTION, ERROR }

enum class DialogEvent {
    DEFAULT, LOGOUT, CANCEL;
    @StringRes
    fun getTitleResId(): Int {
        return when (this) {
            LOGOUT -> R.string.setting_dialog_logout
            CANCEL -> R.string.setting_dialog_cancel
            else -> R.string.setting_dialog_default
        }
    }
    @StringRes
    fun getOkTextResId(): Int {
        return when (this) {
            LOGOUT -> R.string.setting_dialog_logout_btn2
            CANCEL -> R.string.setting_dialog_cancel_btn2
            else -> android.R.string.ok
        }
    }

    @StringRes
    fun getCancelTextResId(): Int {
        return when (this) {
            LOGOUT -> R.string.setting_dialog_logout_btn1
            CANCEL -> R.string.setting_dialog_cancel_btn1
            else -> android.R.string.cancel
        }
    }
}


