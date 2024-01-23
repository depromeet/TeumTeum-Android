package com.teumteum.teumteum.presentation.mypage.setting.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.SettingRepository
import com.teumteum.domain.repository.UserRepository
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

    private val _friendsList = MutableStateFlow<List<Friend>>(emptyList())
    val friendsList : StateFlow<List<Friend>> = _friendsList

    fun checkIfUserIsFriend(friendsList: List<Friend>, userId: Long) {
        _isFriend.value = friendsList.any { friend -> friend.id.toLong() == userId }
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
            FrontCard(
                name = userInfo.name,
                company = "@${userInfo.job.name}",
                job = userInfo.job.detailClass,
                level = "lv.0층",
                area = userInfo.activityArea,
                mbti = userInfo.mbti,
            )
        } else {
            FrontCard() // 기본 FrontCard 인스턴스 반환
        }
    }
    }
