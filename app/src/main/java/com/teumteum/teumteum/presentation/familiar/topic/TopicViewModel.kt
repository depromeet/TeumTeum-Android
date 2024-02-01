package com.teumteum.teumteum.presentation.familiar.topic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.TopicResponse
import com.teumteum.domain.repository.TopicRepository
import com.teumteum.teumteum.util.custom.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections
import javax.inject.Inject

@HiltViewModel
class TopicViewModel @Inject constructor(
    private val topicRepository: TopicRepository
) : ViewModel() {

    private var _topicState = MutableLiveData<UiState>(UiState.Empty)
    val topicState: LiveData<UiState>
        get() = _topicState

    private val _topics = MutableLiveData<List<TopicResponse>>()
    val topics: LiveData<List<TopicResponse>>
        get() = _topics

    fun getTopics(userIds: List<String>) {
        viewModelScope.launch {
            _topicState.value = UiState.Loading

            for (i in 1..5) {
                val type = if (i % 2 == 0) "balance" else "story"
                launch {
                    val response = topicRepository.getTopics(userIds, type).getOrNull()
                    response?.let {
                        withContext(Dispatchers.Main) {
                            updateViewPagerWithApiData(it)
                        }
                    }
                }
            }
        }
    }

    private fun updateViewPagerWithApiData(newApiData: TopicResponse) {
        val currentList = _topics.value.orEmpty().toMutableList()
        currentList.add(newApiData)
        _topics.value = currentList
        // Any other UI update logic goes here
    }
}

