package com.teumteum.teumteum.presentation.familiar.neighbor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.NeighborEntity
import com.teumteum.domain.repository.NeighborRepository
import com.teumteum.domain.repository.RequestPostNeighborUser
import com.teumteum.teumteum.util.custom.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NeighborViewModel @Inject constructor(
    private val neighborRepository: NeighborRepository
) : ViewModel() {

    private var _neighborUserState = MutableLiveData<UiState>(UiState.Empty)
    val neighborUserState: LiveData<UiState>
        get() = _neighborUserState

    var neighborUsers = mutableListOf<NeighborEntity>()

    private var _selectedNeighborIds = MutableLiveData<MutableList<Long>>(mutableListOf())
    val selectedNeighborIds: LiveData<MutableList<Long>>
        get() = _selectedNeighborIds

    fun addSelectedNeighborId(id: Long) {
        val currentList = _selectedNeighborIds.value ?: mutableListOf()
        if (!currentList.contains(id)) {
            currentList.add(id)
            _selectedNeighborIds.value = currentList
        }
    }

    fun removeSelectedNeighborId(id: Long) {
        val currentList = _selectedNeighborIds.value ?: mutableListOf()
        if (currentList.contains(id)) {
            currentList.remove(id)
            _selectedNeighborIds.value = currentList
        }
    }

    fun postNeighborUser(requestPostNeighborUser: RequestPostNeighborUser) {
        viewModelScope.launch {
            runCatching {
                _neighborUserState.value = UiState.Loading
                neighborRepository.postNeighborUser(requestPostNeighborUser = requestPostNeighborUser)
            }.onSuccess {
                if (it.isEmpty()) {
                    _neighborUserState.value = UiState.Failure
                    return@onSuccess
                } else {
                    // id를 기준으로 중복 제거
                    neighborUsers = it.distinctBy { neighborEntity -> neighborEntity.id }.toMutableList()
                    _neighborUserState.value = UiState.Success
                }
            }.onFailure {
                _neighborUserState.value = UiState.Failure
            }
        }
    }

}





