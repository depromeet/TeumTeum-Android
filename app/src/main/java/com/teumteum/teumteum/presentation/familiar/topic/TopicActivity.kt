package com.teumteum.teumteum.presentation.familiar.topic

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.teumteum.base.BindingActivity
import com.teumteum.base.R.color
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.domain.entity.TopicResponse
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityTopicBinding
import com.teumteum.teumteum.presentation.familiar.FamiliarDialogActivity
import com.teumteum.teumteum.presentation.familiar.FamiliarDialogActivity.Companion.EXTRA_SOURCE
import com.teumteum.teumteum.presentation.familiar.FamiliarDialogActivity.Companion.SOURCE_TOPIC
import com.teumteum.teumteum.util.custom.uistate.UiState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class TopicActivity : BindingActivity<ActivityTopicBinding>(R.layout.activity_topic), AppBarLayout {

    private val topicAdapter = TopicAdapter()
    private val viewModel by viewModels<TopicViewModel>()
    private val visitedPages = HashSet<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppBarLayout()
        initViewPager()
        setUpObserver()
        callGetTopicsAndMeasureTime()
    }

    private fun callGetTopicsAndMeasureTime() {
        val userIds = intent.getStringArrayListExtra("userIds") ?: listOf()
        val startTime = System.currentTimeMillis()
        viewModel.getTopics(userIds = userIds)
        viewModel.topicState.observe(this) { state ->
            if (state is UiState.Success || state is UiState.Failure) {
                val endTime = System.currentTimeMillis()
                val totalTime = endTime - startTime
                Timber.tag("총시간").d("$totalTime ms")
            }
        }
    }

    private fun setUpObserver() {
        viewModel.topics.observe(this) { newApiData ->
            updateViewPagerWithApiData(newApiData)
        }

        viewModel.topicState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Handle loading state
                }
                is UiState.Success -> {
                    viewModel.topics.value?.let { newApiDataList ->
                        updateViewPagerWithApiData(newApiDataList)
                    }
                }
                is UiState.Failure -> {
                    // Handle failure state
                }
                else -> {}
            }
        }
    }

    private fun updateViewPagerWithApiData(newApiDataList: List<TopicResponse>) {
        topicAdapter.submitList(newApiDataList) // This is enough for ListAdapter
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)
        setAppBarBackgroundColor(color.background)
        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_close,
                useRippleEffect = false,
                clickEvent = ::startFamiliarDialogActivity
            )
        )
    }

    private fun startFamiliarDialogActivity() {
        val intent = Intent(this, FamiliarDialogActivity::class.java).apply {
            putExtra(EXTRA_SOURCE, SOURCE_TOPIC)
        }
        startActivity(intent)
        finish()
    }

    private fun initViewPager() {
        with(binding) {
            vp.adapter = topicAdapter
            vp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (!visitedPages.contains(position)) {
                        visitedPages.add(position)
                    }
                }
            })
            TabLayoutMediator(binding.tl, binding.vp) { tab, _ ->
                tab.view.isClickable = false
            }.attach()
        }
    }
}