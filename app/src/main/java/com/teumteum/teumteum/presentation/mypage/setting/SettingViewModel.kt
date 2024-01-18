package com.teumteum.teumteum.presentation.mypage.setting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

}

data class SettingUiModel(
    val title: String,
    val url: String = "",
    val onClick: () -> Unit = {}
)


enum class SettingStatus { LOGOUT, SIGNOUT, SIGNOUTCONFIRM,  DEFAULT, NOTION, ERROR }


fun getMemberSetting(): List<SettingUiModel> {
    return listOf(
        SettingUiModel(title = "약관 및 개인정보 처리 동의", onClick = { }),
        SettingUiModel(title = "탈퇴하기", onClick = {  }),
        SettingUiModel(title = "로그아웃", onClick = { })
    )
}

fun getServiceGuide(): List<SettingUiModel> {
    return listOf(
        SettingUiModel(title = "서비스 이용약관", url = "https://sheer-billboard-d63.notion.site/KUSITMS-9e6619383bcd4ce68b6ba4b2b6ef0d40?pvs=4"),
        SettingUiModel(title = "개인정보 처리방침", url = "https://sheer-billboard-d63.notion.site/24a4639559d4433cb89c8f1abb889726?pvs=4")
    )
}

