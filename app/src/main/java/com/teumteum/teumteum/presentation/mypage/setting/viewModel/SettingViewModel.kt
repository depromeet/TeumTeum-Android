package com.teumteum.teumteum.presentation.mypage.setting.viewModel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.teumteum.domain.entity.WithDrawReasons
import com.teumteum.domain.repository.AuthRepository
import com.teumteum.domain.repository.SettingRepository
import com.teumteum.domain.repository.UserRepository
import com.teumteum.teumteum.R
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
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
): ViewModel() {

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
    val dialogEvent: SharedFlow<DialogEvent> = _dialogEvent.asSharedFlow()

    private val _signoutReason = MutableStateFlow<List<String>>(emptyList())
    val signoutReason: StateFlow<List<String>> = _signoutReason.asStateFlow()

    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> = _message.asSharedFlow()

    fun addItem(item: String) {
        if (item !in _signoutReason.value) {
            _signoutReason.value = _signoutReason.value + item
        }
    }
    fun removeItem(item: String) {
        _signoutReason.value = _signoutReason.value - item
    }


    fun resetDialogEvent() {
        viewModelScope.launch {
            _dialogEvent.emit(DialogEvent.DEFAULT)
        }
    }

    fun handleDialogChange(status: SettingStatus) {
        when (status) {
            SettingStatus.CANCEL -> {
                viewModelScope.launch {
                    _dialogEvent.emit(DialogEvent.CANCEL)
                }
            }

            SettingStatus.LOGOUT -> {
                viewModelScope.launch {
                    _dialogEvent.emit(DialogEvent.LOGOUT)
                }
            }

            else -> {}
        }
    }

    private fun sendSignOutReason() {

    }

    fun logout() {
        viewModelScope.launch {
            val logOutResult = settingRepository.logOut()
            logOutResult.onSuccess {
                userRepository.deleteUserInfo()
                authRepository.disableAutoLogin()
                updateSettingStatus(SettingStatus.LOGOUT_CONFIRM)
            }
            logOutResult.onFailure {
                updateSettingStatus(SettingStatus.ERROR)
            }
        }
    }

    fun signout() {
        viewModelScope.launch {
            val signOutReasons = WithDrawReasons(_signoutReason.value)
            val signOutResult = settingRepository.signOut(signOutReasons)
            signOutResult.onSuccess {
                userRepository.deleteUserInfo()
                authRepository.disableAutoLogin()
                updateSettingStatus(SettingStatus.SIGNOUT)
            }
            signOutResult.onFailure {
                updateSettingStatus(SettingStatus.ERROR)
            }
        }
    }

    fun cancelMeeting() {

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


fun getMemberSetting(viewModel: SettingViewModel, navController: NavController): List<SettingUiItem> {
    return listOf(
        SettingUiItem(title = "약관 및 개인정보 처리 동의", onClick = { navController.navigate(R.id.fragment_service) }),
        SettingUiItem(title = "탈퇴하기", onClick = { navController.navigate(R.id.fragment_signout) }),
        SettingUiItem(title = "로그아웃", onClick = { viewModel.updateSettingStatus(SettingStatus.LOGOUT) })
    )
}

fun getServiceGuide(): List<SettingUiItem> {
    return listOf(
        SettingUiItem(title = "서비스 이용약관", url = "https://sheer-billboard-d63.notion.site/KUSITMS-9e6619383bcd4ce68b6ba4b2b6ef0d40?pvs=4"),
        SettingUiItem(title = "개인정보 처리방침", url = "https://sheer-billboard-d63.notion.site/24a4639559d4433cb89c8f1abb889726?pvs=4")
    )
}


enum class SettingStatus {
    LOGOUT, LOGOUT_CONFIRM, SIGNOUT,
    DEFAULT, NOTION, ERROR, SETTING,
    CANCEL, RECOMMEND, RECOMMEND_DETAIL
}


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


