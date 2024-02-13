package com.teumteum.teumteum.presentation.mypage.setting.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.FriendMyPage
import com.teumteum.domain.entity.Review
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.SettingRepository
import com.teumteum.domain.repository.UserRepository
import com.teumteum.teumteum.R
import com.teumteum.teumteum.util.custom.view.model.FrontCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecommendDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val settingRepository: SettingRepository

): ViewModel() {

    private val _userInfoState = MutableStateFlow<UserInfoUiState>(UserInfoUiState.Init)
    val userInfoState: StateFlow<UserInfoUiState> = _userInfoState

    private val _friendInfo = MutableStateFlow<UserInfo?>(null)
    val friendInfo : StateFlow<UserInfo?> = _friendInfo.asStateFlow()

    private val _userMeetingOpen = MutableStateFlow<List<com.teumteum.domain.entity.Meeting>>(emptyList())
    val userMeetingOpen: StateFlow<List<com.teumteum.domain.entity.Meeting>> = _userMeetingOpen

    private val _userMeetingClosed = MutableStateFlow<List<com.teumteum.domain.entity.Meeting>>(emptyList())
    val userMeetingClosed: StateFlow<List<com.teumteum.domain.entity.Meeting>> = _userMeetingClosed

    private val _isFriend = MutableStateFlow<Boolean>(false)
    val isFriend: StateFlow<Boolean> = _isFriend.asStateFlow()

    private val _friendsList = MutableStateFlow<List<FriendMyPage>>(emptyList())
    val friendsList : StateFlow<List<FriendMyPage>> = _friendsList

    private val _reviews = MutableStateFlow<List<UserGrade>>(emptyList())
    val reviews: StateFlow<List<UserGrade>> = _reviews

    fun checkIfUserIsFriend(friendsList: List<FriendMyPage>, userId: Long) {
        _isFriend.value = friendsList.any { friend -> friend.id.toLong() == userId }
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

    fun getReview(userId: Long) {
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

    fun postFriend(userId: Long) {
        if(!isFriend.value) {
            viewModelScope.launch {
                userRepository.postFriend(userId)
                    .onSuccess {
                        _isFriend.value = !_isFriend.value
                    }
                    .onFailure {
                       Timber.e(it)
                    }
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


    fun loadFriends(userId: Long) {
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

    fun loadFriendInfo(userId: Long) {
        if(userId != -1L) {
            viewModelScope.launch {
                _userInfoState.value = UserInfoUiState.Loading
                try {
                    val userInfo = userRepository.getFriendInfo(userId)
                    _userInfoState.value = userInfo?.let { UserInfoUiState.Success(it) }!!
                    _friendInfo.value = userInfo
                    getFrontCardFromInfo()
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    fun getUserOpenMeeting(userId: Long) {
        if (userId != -1L) {
            viewModelScope.launch {
                settingRepository.getMyPageOpenMeeting(userId)
                    .onSuccess { meetings ->
                        val openMeetings = mutableListOf<com.teumteum.domain.entity.Meeting>()
                        _userMeetingOpen.value = openMeetings
                    }
                    .onFailure {
                        Timber.e(it)
                    }
            }
        }
    }

    fun getUserClosedMeeting(userId:Long) {
        if (userId != -1L) {
            viewModelScope.launch {
                settingRepository.getMyPageClosedMeeting(userId)
                    .onSuccess {meetings->
                        val closedMeetings = mutableListOf<com.teumteum.domain.entity.Meeting>()
                        _userMeetingClosed.value = closedMeetings
                    }
                    .onFailure {
                        Timber.e(it)
                        _userInfoState.value = UserInfoUiState.Failure(it.message ?: "정보 불러오기 실패")
                    }
            }
        }
    }

    fun getFrontCardFromInfo(): FrontCard {
        val userInfo = friendInfo.value
        return if (userInfo != null) {
            val characterResId = characterList[userInfo.characterId.toInt()] ?: R.drawable.ic_card_front_penguin// 기본값 설정
            FrontCard(
                name = userInfo.name,
                company = "@${userInfo.job.name}",
                job = userInfo.job.detailClass,
                level = "lv.1층",
                characterResId = characterResId,
                area = userInfo.activityArea,
                mbti = userInfo.mbti,
            )
        } else {
            FrontCard() // 기본 FrontCard 인스턴스 반환
        }
    }
    }
