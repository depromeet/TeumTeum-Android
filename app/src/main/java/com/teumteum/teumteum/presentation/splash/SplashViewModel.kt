package com.teumteum.teumteum.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.AuthRepository
import com.teumteum.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val gsonBuilder = GsonBuilder().create()

    private val _myInfoState = MutableStateFlow<MyInfoUiState>(MyInfoUiState.Init)
    val myInfoState: StateFlow<MyInfoUiState> = _myInfoState

    fun saveUserInfo(userInfo: UserInfo) {
        Timber.tag("teum-datastore").d("saveUserInfo: $userInfo")
        repository.saveUserInfo(gsonBuilder.toJson(userInfo))
    }

    fun getUserInfo(): UserInfo {
        val userInfoGson = gsonBuilder.fromJson(repository.getUserInfo(), UserInfo::class.java)
        Timber.tag("teum-datastore").d("getUserInfo: $userInfoGson")
        return userInfoGson
    }

    fun refreshUserInfo() {
        viewModelScope.launch {
            repository.getMyInfoFromServer()
                .onSuccess {
                    saveUserInfo(userInfo = it)
                    _myInfoState.value = MyInfoUiState.Success
                }
                .onFailure {
                    _myInfoState.value = MyInfoUiState.Failure("회원 정보 로딩 실패")
                }
        }
    }

    fun getIsAutoLogin(): Boolean = authRepository.getAutoLogin()

    fun setIsFirstAfterInstall(isFirst: Boolean) {
        authRepository.setIsFirstAfterInstall(isFirst)
    }

    fun getIsFirstAfterInstall(): Boolean = authRepository.getIsFirstAfterInstall()
}

sealed interface MyInfoUiState {
    object Init: MyInfoUiState
    object Success: MyInfoUiState
    data class Failure(val msg: String): MyInfoUiState
}