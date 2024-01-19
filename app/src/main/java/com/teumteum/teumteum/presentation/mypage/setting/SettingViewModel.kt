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


enum class SettingStatus { LOGOUT, SIGNOUT, SIGNOUTCONFIRM,  DEFAULT, NOTION, ERROR }



