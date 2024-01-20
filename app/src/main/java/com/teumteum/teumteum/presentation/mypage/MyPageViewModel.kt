package com.teumteum.teumteum.presentation.mypage


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.UserRepository
import com.teumteum.teumteum.util.custom.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _userInfoState = MutableStateFlow<UserInfoUiState>(UserInfoUiState.Init)
    val userInfoState: StateFlow<UserInfoUiState> = _userInfoState

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            _userInfoState.value = UserInfoUiState.Loading
            userRepository.getMyInfoFromServer()
                .onSuccess {
                    _userInfoState.value = UserInfoUiState.Success(it)
                }
                .onFailure {
                    _userInfoState.value = UserInfoUiState.Failure("정보 불러오기 실패")
                }
        }
    }
}

sealed interface UserInfoUiState {
    object Init : UserInfoUiState
    object Loading : UserInfoUiState
    data class Success(val data: UserInfo) : UserInfoUiState
    data class Failure(val msg: String) : UserInfoUiState
}

