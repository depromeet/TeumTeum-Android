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
import kotlin.random.Random

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

            val responses = mutableListOf<Job>()

            for (i in 1..5) {
                val type = if (i % 2 == 0) "balance" else "story"
                responses.add(
                    launch {
                        retryFetch { topicRepository.getTopics(userIds, type) }
                    }
                )
            }

            responses.joinAll()
            checkDataAndRetryIfNeeded(userIds)
        }
    }
    private suspend fun retryFetch(fetch: suspend () -> Result<TopicResponse>) {
        var retryCount = 0
        var success = false

        while (retryCount < MAX_RETRY && !success) {
            val response = fetch().getOrNull()
            if (response != null) {
                withContext(Dispatchers.Main) {
                    updateViewPagerWithApiData(response)
                }
                success = true
            } else {
                retryCount++
            }
        }
    }


    private suspend fun checkDataAndRetryIfNeeded(userIds: List<String>) {
        val currentSize = _topics.value?.size ?: 0
        val missingCount = 5 - currentSize

        for (i in 1..missingCount) {
            val type = if (Random.nextBoolean()) "balance" else "story"
            retryFetch { topicRepository.getTopics(userIds, type) }
        }
    }

    companion object {
        const val MAX_RETRY = 3
    }

    private fun updateViewPagerWithApiData(newApiData: TopicResponse) {
        val currentList = _topics.value.orEmpty().toMutableList()
        currentList.add(newApiData)
        _topics.value = currentList
    }
}

