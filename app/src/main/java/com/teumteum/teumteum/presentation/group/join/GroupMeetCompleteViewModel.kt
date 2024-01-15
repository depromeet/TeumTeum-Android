package com.teumteum.teumteum.presentation.group.join

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class GroupMeetCompleteViewModel @Inject constructor(
    private val repository: GroupRepository
): ViewModel() {
    private val _cancelState = MutableStateFlow<GroupMeetCancelUiState>(GroupMeetCancelUiState.Init)
    val cancelState: StateFlow<GroupMeetCancelUiState> = _cancelState

    fun deleteJoinCancel(meetingId: Long) {
        viewModelScope.launch {
            repository.deleteGroupJoin(meetingId)
                .onSuccess {
                    if (it) {
                        _cancelState.value = GroupMeetCancelUiState.Success
                    } else {
                        _cancelState.value = GroupMeetCancelUiState.Failure("모임 참여 신청 취소 서버 통신 실패")
                    }
                }.onFailure {
                    _cancelState.value = GroupMeetCancelUiState.Failure("모임 참여 신청 취소 서버 통신 실패")
                }
        }
    }
}

sealed interface GroupMeetCancelUiState {
    object Init: GroupMeetCancelUiState
    object Success: GroupMeetCancelUiState
    data class Failure(val msg: String): GroupMeetCancelUiState
}