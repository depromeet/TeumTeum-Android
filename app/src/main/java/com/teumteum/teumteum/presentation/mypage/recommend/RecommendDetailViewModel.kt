package com.teumteum.teumteum.presentation.mypage.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.UserInfo
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
    private val userRepository: UserRepository

): ViewModel() {
    private val _friendInfo = MutableStateFlow<UserInfo?>(null)
    val friendInfo : StateFlow<UserInfo?> = _friendInfo.asStateFlow()

    private val _userMeetingOpen = MutableStateFlow<List<com.teumteum.domain.entity.Meeting>>(emptyList())
    val userMeetingOpen: StateFlow<List<com.teumteum.domain.entity.Meeting>> = _userMeetingOpen

    private val _userMeetingClosed = MutableStateFlow<List<com.teumteum.domain.entity.Meeting>>(emptyList())
    val userMeetingClosed: StateFlow<List<com.teumteum.domain.entity.Meeting>> = _userMeetingClosed



    fun loadFriendInfo(userId: Long) {
        viewModelScope.launch {
            try {
                val userInfo = userRepository.getFriendInfo(userId)
                _friendInfo.value = userInfo
                getFrontCardFromInfo()
            } catch (e: Exception) {
                Timber.e(e)
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
                level = "lv.0",
                area = userInfo.activityArea,
                mbti = userInfo.mbti,
                // 기타 필요한 필드
            )
        } else {
            FrontCard() // 기본 FrontCard 인스턴스 반환
        }
    }


    }