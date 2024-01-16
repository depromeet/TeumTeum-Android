package com.teumteum.teumteum.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.AuthTokenModel
import com.teumteum.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _memberState = MutableStateFlow<SignInUiState>(SignInUiState.Init)
    val memberState: StateFlow<SignInUiState> = _memberState

    fun socialLogin(provider: String, code: String) {
        viewModelScope.launch {
            repository.getSocialLogin(provider, code)
                .onSuccess {
                    if (it.isAlreadyMember) {
                        _memberState.value = SignInUiState.Success(it.getAuthToken)
                    }
                    else {
                        _memberState.value = SignInUiState.UserNotRegistered(it.getOauthId)
                    }
                }
                .onFailure {
                    _memberState.value = SignInUiState.Failure("카카오 로그인 실패")
                }
        }
    }

    companion object {
        const val PROVIDER_KAKAO = "kakao"
        const val PROVIDER_NAVER = "naver"
    }
}

sealed interface SignInUiState {
    object Init: SignInUiState
    data class Success(val token: AuthTokenModel): SignInUiState
    data class UserNotRegistered(val oauthId: String): SignInUiState
    data class Failure(val msg: String): SignInUiState
}