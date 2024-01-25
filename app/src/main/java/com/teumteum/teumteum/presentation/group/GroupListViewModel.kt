package com.teumteum.teumteum.presentation.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.Meeting
import com.teumteum.domain.repository.GroupRepository
import com.teumteum.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupListViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _groupData = MutableStateFlow<GroupListUiState>(GroupListUiState.Init)
    val groupData: StateFlow<GroupListUiState> = _groupData

    private var currentPage = 0
    private var isPagingFinish = false

    fun initCurrentPage(location: String? = null, topic: String? = null) {
        currentPage = 0
        isPagingFinish = false
        getGroupList(location, topic)
    }

    fun getGroupList(location: String? = null, topic: String? = null) {
        if (isPagingFinish) return

        viewModelScope.launch {
            groupRepository.getSearchGroup(currentPage++, location = location, topic = topic)
                .onSuccess {
                    if (currentPage == 1) {
                        _groupData.value = when {
                            it.second.isEmpty() -> { GroupListUiState.Empty }
                            else -> { GroupListUiState.SetMeetings(it.second) }
                        }
                    } else if (currentPage > 1) {
                        _groupData.value = GroupListUiState.AddMeetings(it.second)
                    }
                    if (!it.first) isPagingFinish = true
                }.onFailure {
                    _groupData.value = GroupListUiState.Failure("모임 리스트 서버 통신 싪패")
                }
        }
    }

    fun getLocation(): String {
        return userRepository.getUserInfo()?.activityArea ?: ""
    }
}

sealed interface GroupListUiState {
    object Init : GroupListUiState
    object Empty : GroupListUiState
    data class SetMeetings(val data: List<Meeting>) : GroupListUiState
    data class AddMeetings(val data: List<Meeting>) : GroupListUiState
    data class Failure(val msg: String) : GroupListUiState
}