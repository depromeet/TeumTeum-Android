package com.teumteum.teumteum.presentation.familiar.neighbor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.NeighborEntity
import com.teumteum.domain.repository.NeighborRepository
import com.teumteum.teumteum.util.custom.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NeighborViewModel @Inject constructor(
    private val neighborRepository: NeighborRepository,
) : ViewModel() {

    private var _neighborUserState = MutableLiveData<UiState>(UiState.Empty)
    val neighborUserState: LiveData<UiState>
        get() = _neighborUserState


    private var neighborUsers = mutableListOf<NeighborEntity>()

    fun postNeighborUser() {
        viewModelScope.launch {
            runCatching {
                _neighborUserState.value = UiState.Loading
                neighborRepository.postNeighborUser()
            }.onSuccess { //todo - null 처리
                if (it.isEmpty()) {
                    _neighborUserState.value = UiState.Failure
                    return@onSuccess
                } else {
                    neighborUsers = it
                    _neighborUserState.value = UiState.Success
                }
            }.onFailure {
                _neighborUserState.value = UiState.Failure
            }
        }
    }

}





