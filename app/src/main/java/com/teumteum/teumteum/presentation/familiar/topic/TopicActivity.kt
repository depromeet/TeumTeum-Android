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


@AndroidEntryPoint
class TopicActivity
    : BindingActivity<ActivityTopicBinding>(R.layout.activity_topic), AppBarLayout {

    private val topicAdapter = TopicAdapter()
    private val viewModel by viewModels<TopicViewModel>()
    private val visitedPages = HashSet<Int>()
    private var apiDataIndex = 0

    private val dummyList = mutableListOf<TopicResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAppBarLayout()
        initViewPager()
        setUpObserver()
        initDummyViewPager() // Move this after setUpObserver
    }

    private fun initDummyViewPager() {
        for (i in 1..5) {
            // Create instances of Balance and Story as needed
            val balance = TopicResponse.Balance(
                topic = "생성중입니다.",
                balanceQuestion = listOf("Question 1", "Question 2")
            )
            val story = TopicResponse.Story(
                topic = "생성중입니다.",
                story = "Lorem ipsum story $i"
            )

            // Add either Balance or Story to the list based on your logic
            if (i % 2 == 0) { // Alternating between Balance and Story
                dummyList.add(balance)
            } else {
                dummyList.add(story)
            }
        }

        // Initially, combine dummyList and apiDataList to update the ViewPager
        updateViewPagerWithCombinedData()
    }


    private fun setUpObserver() {
        viewModel.topicState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Handle loading state
                }

                is UiState.Success -> {
                    viewModel.topics.value?.let { newApiData ->
                        updateViewPagerWithApiData(newApiData)
                    }
                }

                is UiState.Failure -> {
                    // Handle failure state
                }

                else -> {}
            }
        }
    }

    private fun updateViewPagerWithApiData(newApiData: TopicResponse) {
        if (dummyList.isNotEmpty()) {
            dummyList[apiDataIndex % dummyList.size] = newApiData
            apiDataIndex++
            updateViewPagerWithCombinedData()
        }
    }

    private fun updateViewPagerWithCombinedData() {
        val currentItem = binding.vp.currentItem // 현재 페이지 위치 저장
        topicAdapter.submitList(dummyList.toList()) // 리스트 업데이트
        binding.vp.post {
            binding.vp.setCurrentItem(currentItem, false) // 페이지 위치 복원
        }
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
                        getTopics()
                        visitedPages.add(position)
                    }
                }
            })
            TabLayoutMediator(binding.tl, binding.vp) { tab, _ ->
                tab.view.isClickable = false
            }.attach()
        }
    }

    private fun getTopics() {
        viewModel.getTopics(userIds = listOf("32", "38"), type = "story")
    }
}