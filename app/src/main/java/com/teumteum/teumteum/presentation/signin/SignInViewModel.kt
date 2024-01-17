package com.teumteum.teumteum.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                        repository.setAutoLogin(it.accessToken!!, it.refreshToken!!)
                        _memberState.value = SignInUiState.Success
                    }
                    else {
                        _memberState.value = SignInUiState.UserNotRegistered(it.oauthId!!)
                    }
                }
                .onFailure {
                    _memberState.value = SignInUiState.Failure("카카오 로그인 실패")
                }
        }
    }
}

sealed interface SignInUiState {
    object Init: SignInUiState
    object Success: SignInUiState
    data class UserNotRegistered(val oauthId: String): SignInUiState
    data class Failure(val msg: String): SignInUiState
}