package com.teumteum.teumteum.presentation.group.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.ReviewFriend
import com.teumteum.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val repository: GroupRepository
): ViewModel() {
    private var _selectFriendList = listOf<ReviewFriend>()
    val selectFriendList get() = _selectFriendList

    private val _moimFriendList = MutableStateFlow<List<ReviewFriend>>(listOf())
    val moimFriendList: StateFlow<List<ReviewFriend>> = _moimFriendList

    var meetingId: Long? = null

    fun setSelectFriendList(selectFriendList: List<ReviewFriend>) {
        _selectFriendList = selectFriendList
    }

    fun getReviewFriendList() {
        meetingId?.let { id ->
            viewModelScope.launch {
                repository.getReviewFriendList(id)
                    .onSuccess {
                        _moimFriendList.value = it
                    }
            }
        }
    }
}