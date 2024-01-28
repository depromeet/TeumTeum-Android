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
class TopicViewModel @Inject constructor(
    private val topicRepository: TopicRepository
) : ViewModel() {

    private var _topicState = MutableLiveData<UiState>(UiState.Empty)
    val topicState: LiveData<UiState>
        get() = _topicState

    private val _topics = MutableLiveData<TopicResponse>()
    val topics: LiveData<TopicResponse>
        get() = _topics

    fun getTopics(userIds: List<String>, type: String) {
        viewModelScope.launch {
            _topicState.value = UiState.Loading
            val result = topicRepository.getTopics(userIds, type)
            if (result.isSuccess) {
                result.getOrNull()?.let { topicsResponse ->
                    _topics.value = topicsResponse
                    _topicState.value = UiState.Success
                } ?: run {
                    _topicState.value = UiState.Failure
                }
            } else {
                _topicState.value = UiState.Failure
            }
        }
    }
}

