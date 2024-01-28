package com.teumteum.teumteum.presentation.familiar.topic

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.teumteum.base.BindingActivity
import com.teumteum.base.R.color
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityTopicBinding
import com.teumteum.teumteum.presentation.familiar.topic.model.Topic
import com.teumteum.teumteum.util.custom.uistate.UiState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class TopicActivity
    : BindingActivity<ActivityTopicBinding>(R.layout.activity_topic), AppBarLayout {

    private val topicAdapter = TopicAdapter()
    private val viewpagerList = ArrayList<Topic>()
    private val viewModel by viewModels<TopicViewModel>()
    private val visitedPages = HashSet<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()
        initViewPagerItem()
        initViewPager()
        setUpListener()
        setUpObserver()
    }

    private fun setUpListener() {
    }

    private fun setUpObserver() {
        viewModel.topicState.observe(this) { state ->
            when (state) {
                UiState.Loading -> {}
                UiState.Success -> {
                    Timber.tag("테스트").d("${viewModel.topics.value}")

                }

                UiState.Failure -> {}
                else -> {}
            }
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
                clickEvent = ::finish
            )
        )
    }

    private fun initViewPagerItem() {
        with(viewpagerList) {
            add(
                Topic(
                    topicNumber = "TOPIC.1",
                    topicTitle = "모여서 각자\n디자인",
                    frontImage = ContextCompat.getDrawable(
                        this@TopicActivity,
                        R.drawable.ic_front_balance_background_1
                    ),
                    backImage = ContextCompat.getDrawable(
                        this@TopicActivity,
                        R.drawable.ic_back_balance_background_1
                    )
                )
            )
            add(
                Topic(
                    topicNumber = "TOPIC.2",
                    topicTitle = "모여서 각자\n디자인",
                    frontImage = ContextCompat.getDrawable(
                        this@TopicActivity,
                        R.drawable.ic_front_balance_background_2
                    ),
                    backImage = ContextCompat.getDrawable(
                        this@TopicActivity,
                        R.drawable.ic_back_balance_background_2
                    )
                )
            )
            add(
                Topic(
                    topicNumber = "TOPIC.3",
                    topicTitle = "모여서 각자\n디자인",
                    frontImage = ContextCompat.getDrawable(
                        this@TopicActivity,
                        R.drawable.ic_front_balance_background_3
                    ),
                    backImage = ContextCompat.getDrawable(
                        this@TopicActivity,
                        R.drawable.ic_back_balance_background_3
                    )
                )
            )
            add(
                Topic(
                    topicNumber = "TOPIC.4",
                    topicTitle = "모여서 각자\n디자인",
                    frontImage = ContextCompat.getDrawable(
                        this@TopicActivity,
                        R.drawable.ic_front_balance_background_4
                    ),
                    backImage = ContextCompat.getDrawable(
                        this@TopicActivity,
                        R.drawable.ic_back_balance_background_4
                    )
                )
            )
            add(
                Topic(
                    topicNumber = "TOPIC.5",
                    topicTitle = "모여서 각자\n디자인",
                    frontImage = ContextCompat.getDrawable(
                        this@TopicActivity,
                        R.drawable.ic_front_balance_background_5
                    ),
                    backImage = ContextCompat.getDrawable(
                        this@TopicActivity,
                        R.drawable.ic_back_balance_background_5
                    )

                )
            )
        }
    }

    private fun initViewPager() {
        topicAdapter.submitList(viewpagerList)
        with(binding) {
            vp.adapter = topicAdapter
            vp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (!visitedPages.contains(position)) {//방문하지 않은 페이지에서만 api 호출
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