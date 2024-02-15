package com.teumteum.teumteum.presentation.group.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.ReviewFriend
import com.teumteum.domain.repository.GroupRepository
import com.teumteum.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val repository: GroupRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private var _selectFriendList = listOf<ReviewFriend>()
    val selectFriendList get() = _selectFriendList

    private var _selectDetailFriendList = mutableListOf<ReviewFriend>()
    val selectDetailFriendList get() = _selectDetailFriendList

    var meetingId: Long? = null

    var currentFriendIndex = 0

    private val _moimFriendList = MutableStateFlow<List<ReviewFriend>>(listOf())
    val moimFriendList: StateFlow<List<ReviewFriend>> = _moimFriendList

    private val _reviewState = MutableStateFlow<ReviewUiState>(ReviewUiState.Init)
    val reviewState: StateFlow<ReviewUiState> = _reviewState
    fun setSelectFriendList(selectFriendList: List<ReviewFriend>) {
        _selectFriendList = selectFriendList
    }

    fun addSelectDetailFriendList(selectFriendDetail: ReviewFriend) {
        _selectDetailFriendList.add(selectFriendDetail)
    }

    private fun filterReviewFriendList(list: List<ReviewFriend>): List<ReviewFriend> {
        val friendList = list.toMutableList()
        val myUserId = userRepository.getUserInfo()?.id
        friendList.removeAll { it.id == myUserId }
        return friendList
    }

    fun getReviewFriendList() {
        meetingId?.let { id ->
            viewModelScope.launch {
                repository.getReviewFriendList(id)
                    .onSuccess { list ->
                        _moimFriendList.value = filterReviewFriendList(list)
                    }
            }
        }
    }

    fun postRegisterReview() {
        meetingId?.let { id ->
            _reviewState.value = ReviewUiState.Init
            viewModelScope.launch {
                repository.postRegisterReview(id, selectDetailFriendList)
                    .onSuccess {
                        _reviewState.value =
                            if (it) ReviewUiState.Success else ReviewUiState.Failure("리뷰 등록 서버 통신에 실패했습니다.")
                    }.onFailure {
                        _reviewState.value = ReviewUiState.Failure("리뷰 등록 서버 통신에 실패했습니다.")
                    }
            }
        }
    }
}

sealed interface ReviewUiState {
    object Init : ReviewUiState
    object Success : ReviewUiState
    data class Failure(val msg: String) : ReviewUiState
}