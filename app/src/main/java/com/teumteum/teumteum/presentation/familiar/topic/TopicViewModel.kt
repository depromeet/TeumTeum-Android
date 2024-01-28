package com.teumteum.teumteum.presentation.familiar.topic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.TopicResponse
import com.teumteum.domain.repository.TopicRepository
import com.teumteum.teumteum.util.custom.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(private val topicRepository: TopicRepository) :
    ViewModel() {

    private var _topicState = MutableLiveData<UiState>(UiState.Empty)
    val topicState: LiveData<UiState>
        get() = _topicState


    fun getTopics(userIds: List<String>, type: String) {
        viewModelScope.launch {
            _topicState.value = UiState.Loading
            val result = runCatching {
                topicRepository.getTopics(userIds, type)
            }

            result.onSuccess { topicsResponse ->
                when (val response = topicsResponse.getOrNull()) {
                    is TopicResponse.Balance -> {
                        //todo - 여기로 빠지는 data 담을 수 있는 전용 liveData 만들기

                        // Process Balance response
                        // Update LiveData or UI state
                        _topicState.value = UiState.Success
                    }

                    is TopicResponse.Story -> {
                        //todo - 여기로 빠지는 data 담을 수 있는 전용 liveData 만들기

                        // Process Story response
                        // Update LiveData or UI state
                        _topicState.value = UiState.Success
                    }

                    else -> {
                        _topicState.value = UiState.Failure
                    }
                }
            }.onFailure {
                // Log error or update UI state to reflect the failure
                _topicState.value = UiState.Failure
            }
        }
    }
}




