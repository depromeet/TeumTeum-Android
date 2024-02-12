package com.teumteum.teumteum.presentation.mypage.setting.viewModel

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.teumteum.domain.entity.UserInfo
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
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
): ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()

    private val _userBirthDate = MutableStateFlow("")
    val userBirthDate = _userBirthDate.asStateFlow()

    private val _userAuth = MutableStateFlow("")
    val userAuth = _userAuth.asStateFlow()

    private val originalUserInfo = MutableStateFlow<UserInfo?>(null)

    private val _userOpenMeetingList = MutableStateFlow<List<com.teumteum.domain.entity.Meeting>>(emptyList())
    val userOpenMeetingList: StateFlow<List<com.teumteum.domain.entity.Meeting>> = _userOpenMeetingList

    private val _userClosedMeetingList = MutableStateFlow<List<com.teumteum.domain.entity.Meeting>>(emptyList())
    val userClosedMeetingList: StateFlow<List<com.teumteum.domain.entity.Meeting>> = _userClosedMeetingList

    private val _userHostOpenMeetingList = MutableStateFlow<List<com.teumteum.domain.entity.Meeting>>(emptyList())
    val userHostMeetingList: StateFlow<List<com.teumteum.domain.entity.Meeting>> = _userHostOpenMeetingList

    private val _userHostClosedMeetingList = MutableStateFlow<List<com.teumteum.domain.entity.Meeting>>(emptyList())
    val userHostClosedMeetingList: StateFlow<List<com.teumteum.domain.entity.Meeting>> = _userHostClosedMeetingList

    init {
        loadUserInfo()
        getUserClosedMeeting()
        getUserOpenMeeting()
    }

    private fun loadUserInfo() = viewModelScope.launch {
        originalUserInfo.value = userRepository.getUserInfo()
        originalUserInfo.value?.let {
            _userName.value = it.name
            _userBirthDate.value = it.birth
            _userAuth.value = it.authenticated
        }
    }

    fun getUserOpenMeeting() {
        val userId = authRepository.getUserId()
//        val userId = 16.toLong()
        if (userId != -1L) {
            viewModelScope.launch {
                settingRepository.getMyPageOpenMeeting(userId)
                    .onSuccess { meetings ->
                        val openMeetings = mutableListOf<com.teumteum.domain.entity.Meeting>()
                        val hostMeetings = mutableListOf<com.teumteum.domain.entity.Meeting>()
                        meetings.forEach { meeting ->
                            if (meeting.hostId == userId) {
                                hostMeetings.add(meeting)
                            } else {
                                openMeetings.add(meeting)
                            }
                        }
                        _userOpenMeetingList.value = openMeetings
                        _userHostOpenMeetingList.value = hostMeetings
                    }
                    .onFailure {
                        Timber.e(it)
                        updateSettingStatus(SettingStatus.ERROR)
                    }
            }
        }
    }

    fun getUserClosedMeeting() {
                val userId = authRepository.getUserId()
//        val userId = 16.toLong()
        if (userId != -1L) {
            viewModelScope.launch {
                settingRepository.getMyPageClosedMeeting(userId)
                    .onSuccess { meetings ->
                        val closedMeetings = mutableListOf<com.teumteum.domain.entity.Meeting>()
                        val hostMeetings = mutableListOf<com.teumteum.domain.entity.Meeting>()
                        meetings.forEach { meeting ->
                            if (meeting.hostId == userId) {
                                hostMeetings.add(meeting)
                            } else {
                                closedMeetings.add(meeting)
                            }
                        }
                        _userClosedMeetingList.value = closedMeetings
                        _userHostClosedMeetingList.value = hostMeetings
                    }
                    .onFailure {
                        Timber.e(it)
                        updateSettingStatus(SettingStatus.ERROR)
                    }
            }
        }
    }

    fun updateUserName(newName: String) {
        _userName.value = newName
    }

    fun updateUserBirthDate(newBirthDate: String) {
        _userBirthDate.value = newBirthDate
    }


    fun isUserInfoChanged(): Boolean {
        originalUserInfo.value?.let { original ->
            val currentInfo = original.copy(
                name = _userName.value,
                birth = _userBirthDate.value
            )
            return currentInfo != original
        }
        return false
    }

    private val _alarmState = MutableStateFlow(settingRepository.getNotification())
    val alarmState: StateFlow<Boolean> = _alarmState.asStateFlow()

    fun onToggleChange(newToggleState: Boolean) {
        settingRepository.setNotification(newToggleState)
        _alarmState.value = settingRepository.getNotification()
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

    private val _isCheckboxChecked = MutableStateFlow(false)
    val isCheckboxChecked: StateFlow<Boolean> = _isCheckboxChecked.asStateFlow()

    fun toggleCheckbox() {
        _isCheckboxChecked.value = !_isCheckboxChecked.value
    }

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
            SettingStatus.LOGOUT -> {
                viewModelScope.launch {
                    _dialogEvent.emit(DialogEvent.LOGOUT)
                }
            }

            else -> {}
        }
    }
        fun updateUserInfo() = viewModelScope.launch {
            if (isUserInfoChanged()) {
                originalUserInfo.value?.let { original ->
                    val updatedUserInfo = original.copy(
                        name = _userName.value,
                        birth = _userBirthDate.value
                    )
                    userRepository.updateUserInfo(updatedUserInfo)
                        .onSuccess {
                            Log.d("업데이트 성공", "성공")
                        }
                        .onFailure {
                            updateSettingStatus(SettingStatus.ERROR)
                            Timber.e(it)
                        }
                }
            }
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
                Timber.e(it)
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
                Timber.e(it)
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

            else -> {}
        }
    }
}


fun getMemberSetting(viewModel: SettingViewModel, navController: NavController): List<SettingUiItem> {
    return listOf(
        SettingUiItem(title = "약관 및 개인정보 처리 동의", onClick = { navController.navigate(R.id.fragment_service) }),
        SettingUiItem(title = "탈퇴하기", onClick = { navController.navigate(R.id.fragment_signout) }),
        SettingUiItem(title = "로그아웃", onClick = { viewModel.updateSettingStatus(SettingStatus.LOGOUT) }),
        SettingUiItem(title = "오픈소스", onClick = {viewModel.updateSettingStatus(SettingStatus.OPENSOURCE)})
    )
}

fun getServiceGuide(): List<SettingUiItem> {
    return listOf(
        SettingUiItem(title = "서비스 이용약관", url = "https://www.notion.so/a9258de57a984838bada4eb32c30b730?pvs=4"),
        SettingUiItem(title = "개인정보 처리방침", url = "https://www.notion.so/bcede3e738c647ba97a4335830540d2f?pvs=4")
    )
}


enum class SettingStatus {
    LOGOUT, LOGOUT_CONFIRM, SIGNOUT,
    DEFAULT, NOTION, ERROR, OPENSOURCE
}


enum class DialogEvent {
    DEFAULT, LOGOUT;

    @StringRes
    fun getTitleResId(): Int {
        return when (this) {
            LOGOUT -> R.string.setting_dialog_logout
            else -> R.string.setting_dialog_default
        }
    }

    @StringRes
    fun getOkTextResId(): Int {
        return when (this) {
            LOGOUT -> R.string.setting_dialog_logout_btn2
            else -> android.R.string.ok
        }
    }

    @StringRes
    fun getCancelTextResId(): Int {
        return when (this) {
            LOGOUT -> R.string.setting_dialog_logout_btn1
            else -> android.R.string.cancel
        }
    }
}



