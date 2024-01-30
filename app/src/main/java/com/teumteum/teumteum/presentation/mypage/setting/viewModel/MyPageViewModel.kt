package com.teumteum.teumteum.presentation.mypage.setting.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.FriendMyPage
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.AuthRepository
import com.teumteum.domain.repository.UserRepository
import com.teumteum.teumteum.R
import com.teumteum.teumteum.util.custom.view.model.BackCard
import com.teumteum.teumteum.util.custom.view.model.FrontCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _userInfoState = MutableStateFlow<UserInfoUiState>(UserInfoUiState.Init)
    val userInfoState: StateFlow<UserInfoUiState> = _userInfoState

    private val _frontCardState = MutableStateFlow(FrontCard())
    val frontCardState: StateFlow<FrontCard> = _frontCardState

    private val _backCardState = MutableStateFlow(BackCard())
    val backCardState: StateFlow<BackCard> = _backCardState

    private val _friendsList = MutableStateFlow<List<FriendMyPage>>(emptyList())
    val friendsList : StateFlow<List<FriendMyPage>> = _friendsList

    private val _isFrontCardShown = MutableStateFlow(true)
    val isFrontCardShown: StateFlow<Boolean> = _isFrontCardShown

    init {
        loadUserInfo()
        loadFriends()
    }

    fun toggleCardState() {
        _isFrontCardShown.value = !_isFrontCardShown.value
    }

    fun loadFriends() {
        val userId = authRepository.getUserId()
        if (userId != -1L) {
            viewModelScope.launch {
                userRepository.getUserFriends(userId)
                    .onSuccess {
                        _friendsList.value = it.friends
                    }
                    .onFailure {
                        Timber.e(it)
                    }
                }
            }
        }

    fun loadUserInfo() {
        viewModelScope.launch {
            _userInfoState.value = UserInfoUiState.Loading
            userRepository.getMyInfoFromServer()
                .onSuccess {
                    _userInfoState.value = UserInfoUiState.Success(it)
                    // 카드 - userInfo 매칭
                    val frontCardData = userInfoToFrontCard(it, characterList)
                    val backCardData = userInfoToBackCard(it, characterListBack)
                    _userInfoState.value = UserInfoUiState.Success(it)
                    _frontCardState.value = frontCardData
                    _backCardState.value = backCardData
                }
                .onFailure {
                    _userInfoState.value = UserInfoUiState.Failure("정보 불러오기 실패")
                }
        }
    }
    val characterList: HashMap<Int, Int> = hashMapOf(
        0 to R.drawable.ic_card_front_ghost,
        1 to R.drawable.ic_card_front_star,
        2 to R.drawable.ic_card_front_bear,
        3 to R.drawable.ic_card_front_raccon,
        4 to R.drawable.ic_card_front_cat,
        5 to R.drawable.ic_card_front_rabbit,
        6 to R.drawable.ic_card_front_fox,
        7 to R.drawable.ic_card_front_water,
        8 to R.drawable.ic_card_front_penguin,
        9 to R.drawable.ic_card_front_dog,
        10 to R.drawable.ic_card_front_mouse,
        11 to R.drawable.ic_card_front_panda
    )

    val characterListBack: HashMap<Int, Int> = hashMapOf(
        0 to R.drawable.ic_card_back_ghost,
        1 to R.drawable.ic_card_back_star,
        2 to R.drawable.ic_card_back_bear,
        3 to R.drawable.ic_card_back_raccon,
        4 to R.drawable.ic_card_back_cat,
        5 to R.drawable.ic_card_back_rabbit,
        6 to R.drawable.ic_card_back_fox,
        7 to R.drawable.ic_card_back_water,
        8 to R.drawable.ic_card_back_penguin,
        9 to R.drawable.ic_card_back_dog,
        10 to R.drawable.ic_card_back_mouse,
        11 to R.drawable.ic_card_back_panda
    )

    val friendCharacterList: HashMap<Int, Int> = hashMapOf(
        0 to R.drawable.ic_ghost,
        1 to R.drawable.ic_star,
        2 to R.drawable.ic_bear,
        3 to R.drawable.ic_raccoon,
        4 to R.drawable.ic_cat,
        5 to R.drawable.ic_rabbit,
        6 to R.drawable.ic_fox,
        7 to R.drawable.ic_water,
        8 to R.drawable.ic_penguin,
        9 to R.drawable.ic_dog,
        10 to R.drawable.ic_mouse,
        11 to R.drawable.ic_panda
    )

    fun userInfoToFrontCard(userInfo: UserInfo, characterList: HashMap<Int, Int>): FrontCard {
        return FrontCard(
            name = userInfo.name,
            company = "@${userInfo.job.name}" ?: "@직장,학교명",
            job = userInfo.job.detailClass,
            level = "lv.${userInfo.mannerTemperature}층",
            area = "${userInfo.activityArea}에 사는",
            mbti = userInfo.mbti,
            characterResId = characterList[userInfo.characterId.toInt()] ?: 1,
        )
    }

    fun userInfoToBackCard(userInfo: UserInfo, characterList: HashMap<Int, Int>): BackCard {
        return BackCard(
            goalTitle = "GOAL",
            goalContent = userInfo.goal,
            interests = userInfo.interests.toMutableList(),
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

