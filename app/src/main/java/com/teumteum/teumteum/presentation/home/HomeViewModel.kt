package com.teumteum.teumteum.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.RecommendMeetEntity
import com.teumteum.domain.repository.HomeRepository
import com.teumteum.teumteum.util.custom.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {

    val recommendMeets = MutableLiveData<List<RecommendMeetEntity>?>()

    private var _recommendMeetState = MutableLiveData<UiState>(UiState.Empty)
    val recommendMeetState: LiveData<UiState>
        get() = _recommendMeetState


    fun getRecommendMeet() {
        viewModelScope.launch {
            runCatching {
                _recommendMeetState.value = UiState.Loading
                homeRepository.getRecommendMeet()
            }.onSuccess {
//                if (it != null) {
//                    recommendMeets.value = it
//                } else {
//                    recommendMeets.value = null
//                }
                _recommendMeetState.value = UiState.Success
            }.onFailure {
//                Timber.e(it)
                _recommendMeetState.value = UiState.Failure
            }
        }
    }
}




