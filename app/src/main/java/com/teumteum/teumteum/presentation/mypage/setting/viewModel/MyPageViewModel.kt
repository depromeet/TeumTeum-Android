package com.teumteum.teumteum.presentation.mypage.setting.viewModel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.FriendMyPage
import com.teumteum.domain.entity.Review
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.AuthRepository
import com.teumteum.domain.repository.UserRepository
import com.teumteum.teumteum.R
import com.teumteum.teumteum.util.SignupUtils.CHARACTER_CARD_LIST
import com.teumteum.teumteum.util.SignupUtils.CHARACTER_CARD_LIST_BACK
import com.teumteum.teumteum.util.custom.view.model.BackCard
import com.teumteum.teumteum.util.custom.view.model.FrontCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
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

    private val _interests = MutableStateFlow<List<String>>(emptyList())
    val interests: StateFlow<List<String>> = _interests.asStateFlow()

    private val _reviews = MutableStateFlow<List<UserGrade>>(emptyList())
    val reviews: StateFlow<List<UserGrade>> = _reviews


    init {
        loadUserInfo()
        loadFriends()
        getReview()
    }

    fun reviewToUserGrade(reviews:List<Review>): List<UserGrade> {
        return reviews.map {
            UserGrade(
                text = it.review,
                count = it.count,
                image = when (it.review) {
                    "최고에요" -> R.drawable.ic_grade_exel
                    "좋아요" -> R.drawable.ic_grade_good
                    "별로에요" -> R.drawable.ic_grade_bad
                    else -> R.drawable.ic_grade_bad
                }
            )
        }
    }

    fun getReview() {
        val userId =authRepository.getUserId()
        if(userId != -1L) {
            viewModelScope.launch {
                userRepository.getUserReview(userId)
                    .onSuccess {
                        val sortedUserGrades = reviewToUserGrade(it).sortedBy { userGrade ->
                            when (userGrade.text) {
                                "최고에요" -> 1
                                "좋아요" -> 2
                                "별로에요" -> 3
                                else -> 4
                            }
                        }
                        _reviews.value = sortedUserGrades
                    }
                    .onFailure {
                        Timber.e(it)
                    }
            }
        }
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
                    val frontCardData = userInfoToFrontCard(it, CHARACTER_CARD_LIST)
                    val backCardData = userInfoToBackCard(it, CHARACTER_CARD_LIST_BACK)
                    _userInfoState.value = UserInfoUiState.Success(it)
                    _frontCardState.value = frontCardData
                    _backCardState.value = backCardData
                }
                .onFailure {
                    _userInfoState.value = UserInfoUiState.Failure("정보 불러오기 실패")
                }
        }
    }

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
        _interests.value = userInfo.interests.toMutableList()
        return BackCard(
            goalTitle = "GOAL",
            goalContent = userInfo.goal,
            interests = interests.value.toMutableList(),
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

