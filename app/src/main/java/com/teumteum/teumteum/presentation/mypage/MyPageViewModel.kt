package com.teumteum.teumteum.presentation.mypage


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.UserRepository
import com.teumteum.teumteum.R
import com.teumteum.teumteum.util.custom.uistate.UiState
import com.teumteum.teumteum.util.custom.view.model.FrontCard
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

    private val _frontCardState = MutableStateFlow(FrontCard())
    val frontCardState: StateFlow<FrontCard> = _frontCardState

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            _userInfoState.value = UserInfoUiState.Loading
            userRepository.getMyInfoFromServer()
                .onSuccess {
                    _userInfoState.value = UserInfoUiState.Success(it)
                    // 카드 - userInfo 매칭
                    val frontCardData = userInfoToFrontCard(it, characterList)
                    _userInfoState.value = UserInfoUiState.Success(it)
                    _frontCardState.value = frontCardData
                }
                .onFailure {
                    _userInfoState.value = UserInfoUiState.Failure("정보 불러오기 실패")
                }
        }
    }
    private val characterList: HashMap<Int, Int> = hashMapOf(
        0 to R.drawable.ic_card_ghost,
        1 to R.drawable.ic_card_star,
        2 to R.drawable.ic_card_bear,
        3 to R.drawable.ic_card_raccon,
        4 to R.drawable.ic_card_cat,
        5 to R.drawable.ic_card_rabbit,
        6 to R.drawable.ic_card_fox,
        7 to R.drawable.ic_card_water,
        8 to R.drawable.ic_card_penguin,
        9 to R.drawable.ic_card_dog,
        10 to R.drawable.ic_card_mouse,
        11 to R.drawable.ic_card_panda
    )


    fun userInfoToFrontCard(userInfo: UserInfo, characterList: HashMap<Int, Int>): FrontCard {
        return FrontCard(
            name = userInfo.name,
            company = userInfo.job.name ?: "@직장,학교명",
            job = userInfo.job.detailClass,
            level = "lv.${userInfo.mannerTemperature}층",
            area = "${userInfo.activityArea}에 사는",
            mbti = userInfo.mbti,
            characterResId = characterList[userInfo.characterId.toInt()] ?: 1,
        )
    }
}

sealed interface UserInfoUiState {
    object Init : UserInfoUiState
    object Loading : UserInfoUiState
    data class Success(val data: UserInfo) : UserInfoUiState
    data class Failure(val msg: String) : UserInfoUiState
}

