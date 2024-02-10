package com.teumteum.teumteum.presentation.familiar.shaketopic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.TopicResponse
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.TopicRepository
import com.teumteum.domain.repository.UserRepository
import com.teumteum.teumteum.util.custom.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Collections
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ShakeTopicViewModel @Inject constructor(
    private val topicRepository: TopicRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _friends = MutableLiveData<List<Friend>>()
    val friends: LiveData<List<Friend>>
        get() = _friends

    fun getUserInfo(): UserInfo? {
        return userRepository.getUserInfo() //todo - 예외 처리
    }

    fun setFriendsData(friends: List<Friend>) {
        _friends.value = friends
    }

    private val _topics = MutableLiveData<List<TopicResponse>>()
    val topics: LiveData<List<TopicResponse>>
        get() = _topics

    private var isFetching = false

    fun getTopics(userIds: List<String>) {
        if (isFetching || (_topics.value?.size ?: 0) >= 5) {
            // 이미 API 호출 중이거나 필요한 데이터를 모두 받았다면 요청하지 않습니다.
            return
        }
        isFetching = true

        viewModelScope.launch {
            val responses = mutableListOf<Job>()

            for (i in 1..5) {
                val type = if (i % 2 == 0) "story" else "balance"
                if ((_topics.value?.size ?: 0) < 5) { // 이미 5개의 데이터를 받았다면 추가 호출을 하지 않습니다.
                    responses.add(
                        launch {
                            retryFetch { topicRepository.getTopics(userIds, type) }
                        }
                    )
                }
            }

            responses.joinAll()
            isFetching = false
            // 'checkDataAndRetryIfNeeded' 함수 호출 부분을 제거합니다.
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
                    // 받은 데이터가 5개면 더 이상 재시도하지 않습니다.
                    if (_topics.value?.size == 5) {
                        Timber.d("data 5개 다 받아옴")
                        return@withContext
                    }
                }
                success = true
            } else {
                retryCount++
            }
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

