package com.teumteum.teumteum.presentation.signin

import androidx.lifecycle.ViewModel
import com.teumteum.domain.entity.SocialLoginResult
import com.teumteum.domain.repository.AuthRepository
import com.teumteum.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val settingRepository: SettingRepository
): ViewModel() {

    private val _memberState = MutableStateFlow<SignInUiState>(SignInUiState.Init)
    val memberState: StateFlow<SignInUiState> = _memberState

    var oauthId = ""

    fun updateMemberState(socialLoginResult: SocialLoginResult) {
        if (socialLoginResult.message == null) {
            if (!socialLoginResult.accessToken.isNullOrEmpty() && !socialLoginResult.refreshToken.isNullOrEmpty()) {
                // 기존 회원일 때
                repository.setAutoLogin(
                    socialLoginResult.accessToken!!,
                    socialLoginResult.refreshToken!!)
                _memberState.value = SignInUiState.Success
            }
            else if (!socialLoginResult.oauthId.isNullOrEmpty()){
                // 새로 가입해야 할 때
                oauthId = socialLoginResult.oauthId!!
                _memberState.value = SignInUiState.UserNotRegistered
            }
            else {
                _memberState.value = SignInUiState.Failure("소셜로그인 실패")
            }
        }
        else {
            // 통신 실패
            _memberState.value = SignInUiState.Failure(socialLoginResult.message!!)
        }
    }

    fun getAskedNotification(): Boolean = repository.getAskedNotification()

    fun setAskedkNotification(didAsk: Boolean) {
        repository.setAskedNotification(didAsk)
    }

    fun setOnNotification(isOn: Boolean) {
        settingRepository.setNotification(isOn)
    }
}

sealed interface SignInUiState {
    object Init: SignInUiState
    object Success: SignInUiState
    object UserNotRegistered: SignInUiState
    data class Failure(val msg: String): SignInUiState
}