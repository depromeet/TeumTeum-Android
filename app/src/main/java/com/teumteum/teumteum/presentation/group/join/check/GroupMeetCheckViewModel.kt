package com.teumteum.teumteum.presentation.group.join.check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.Meeting
import com.teumteum.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class GroupMeetCheckViewModel @Inject constructor(
    private val repository: GroupRepository
): ViewModel() {
    private val _joinState = MutableStateFlow<MeetCheckUiState>(MeetCheckUiState.Init)
    val joinState: StateFlow<MeetCheckUiState> = _joinState

    fun joinGroup(meetingId: Long) {
        viewModelScope.launch {
            repository.postJoinGroup(meetingId)
                .onSuccess {
                    _joinState.value = MeetCheckUiState.Success(it)
                }.onFailure {
                    _joinState.value = MeetCheckUiState.Failure("모임 참여 서버 통신 실패")
                }
        }
    }
}

sealed interface MeetCheckUiState {
    object Init: MeetCheckUiState
    data class Success(val data: Meeting): MeetCheckUiState
    data class Failure(val msg: String): MeetCheckUiState
}