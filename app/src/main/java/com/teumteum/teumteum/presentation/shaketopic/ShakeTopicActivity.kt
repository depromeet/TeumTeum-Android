package com.teumteum.teumteum.presentation.shaketopic

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.teumteum.base.BindingActivity
import com.teumteum.domain.entity.Friend
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityShakeTopicBinding
import com.teumteum.teumteum.presentation.familiar.introduce.IntroduceActivity.Companion.EXTRA_FRIENDS
import com.teumteum.teumteum.presentation.shaketopic.shake.ShakeFragment
import com.teumteum.teumteum.presentation.shaketopic.topic.TopicFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ShakeTopicActivity :
    BindingActivity<ActivityShakeTopicBinding>(R.layout.activity_shake_topic) {

    private val viewModel by viewModels<ShakeTopicViewModel>()
    private var isShakeCompleted = false
    private var isApiDataReceived = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        passDataToViewModel()
        setUpObserver()
    }

    private fun initView() {
        val initialFragment = ShakeFragment()
        supportFragmentManager.commit {
            add(R.id.fl_main, initialFragment)
        }
    }

    private fun setUpObserver() {
        viewModel.topics.observe(this) { topics ->
            if (topics.size >= 2 && !isApiDataReceived) {
                onApiDataReceived()
            }
        }
    }
    fun onShakeCompleted() {
        Timber.d("흔들기 3초 완료")
        isShakeCompleted = true
        checkAndShowTopicFragment()
    }

    fun onApiDataReceived() {
        Timber.d("데이터 최소 2개 수신 완료")
        isApiDataReceived = true
        checkAndShowTopicFragment()
    }

    private fun checkAndShowTopicFragment() {
        Timber.d("조건 2개 충족 완료 페이지 이동 실행")
        if (isShakeCompleted && isApiDataReceived) {
            showTopicFragment()
        }
    }

    private fun showTopicFragment() {
        val topicFragment = TopicFragment()
        supportFragmentManager.commit {
            replace(R.id.fl_main, topicFragment)
            addToBackStack(null)
        }
    }

    private fun passDataToViewModel() {
        val friends = intent.getSerializableExtra(EXTRA_FRIENDS) as? List<Friend>
        viewModel.setFriendsData(friends ?: listOf())
    }

    companion object {}
}