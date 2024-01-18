package com.teumteum.teumteum.presentation.signin

import androidx.lifecycle.ViewModel
import com.teumteum.domain.entity.SocialLoginResult
import com.teumteum.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _memberState = MutableStateFlow<SignInUiState>(SignInUiState.Init)
    val memberState: StateFlow<SignInUiState> = _memberState

    var oauthId = ""

    fun updateMemberState(socialLoginResult: SocialLoginResult) {
//        Timber.tag("teum-signin").d("socialLoginResult: ${socialLoginResult.toString()}")
        if (socialLoginResult.messages == null) {
            if (socialLoginResult.isAlreadyMember) {
                // 기존 회원일 때
                Timber.tag("teum-signin").d("isAlreadyMember")
                repository.setAutoLogin(
                    socialLoginResult.accessToken!!,
                    socialLoginResult.refreshToken!!)
                _memberState.value = SignInUiState.Success
            }
            else {
                // 새로 가입해야 할 때
                Timber.tag("teum-signin").d("isNotAlreadyMember")
                oauthId = socialLoginResult.oauthId!!
                _memberState.value = SignInUiState.UserNotRegistered
            }
        }
        else {
            // 통신 실패
            Timber.tag("teum-signin").d("socialLoginResult: Faliure")
            _memberState.value = SignInUiState.Failure(socialLoginResult.messages!!)
        }
    }
}

sealed interface SignInUiState {
    object Init: SignInUiState
    object Success: SignInUiState
    object UserNotRegistered: SignInUiState
    data class Failure(val msg: String): SignInUiState
}