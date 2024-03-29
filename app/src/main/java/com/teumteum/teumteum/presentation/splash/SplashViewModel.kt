package com.teumteum.teumteum.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.AuthRepository
import com.teumteum.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _myInfoState = MutableStateFlow<MyInfoUiState>(MyInfoUiState.Init)
    val myInfoState: StateFlow<MyInfoUiState> = _myInfoState

    private val _myInfo = MutableLiveData<UserInfo>()
    val myInfo: LiveData<UserInfo> = _myInfo


    private fun saveUserInfo(userInfo: UserInfo) {
        repository.saveUserInfo(userInfo)
    }

    fun getUserInfo(): UserInfo? {
        val userInfo = repository.getUserInfo()
        return if (getIsAutoLogin()) {
            userInfo
        } else null
    }

    // 유저 정보 datastore에 재저장
    fun refreshUserInfo() {
        viewModelScope.launch {
            repository.getMyInfoFromServer()
                .onSuccess {
                    saveUserInfo(userInfo = it)
                    patchDeviceToken()
                    _myInfo.value = it
                    _myInfoState.value = MyInfoUiState.Success
                }
                .onFailure {
                    authRepository.disableAutoLogin()
                    _myInfoState.value = MyInfoUiState.Failure("회원 정보 로딩 실패")
                }
        }
    }

    fun getIsAutoLogin(): Boolean = authRepository.getAutoLogin()

    fun setIsFirstAfterInstall(isFirst: Boolean) {
        authRepository.setIsFirstAfterInstall(isFirst)
    }

    fun getIsFirstAfterInstall(): Boolean = authRepository.getIsFirstAfterInstall()

    private fun postDeviceToken() {
        val deviceToken = authRepository.getDeviceToken()
        if (deviceToken.isNotBlank()) {
            viewModelScope.launch {
                authRepository.postDeviceToken(deviceToken)
            }
        }
    }

    private fun patchDeviceToken() {
        val deviceToken = authRepository.getDeviceToken()
        if (deviceToken.isNotBlank()) {
            viewModelScope.launch {
                val result = authRepository.patchDeviceToken(deviceToken)
                if (!result) postDeviceToken()
            }
        }
    }
}

sealed interface MyInfoUiState {
    object Init : MyInfoUiState
    object Success : MyInfoUiState
    data class Failure(val msg: String) : MyInfoUiState
}