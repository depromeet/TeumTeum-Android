package com.teumteum.teumteum.presentation.familiar.shaketopic.topic

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.domain.entity.TopicResponse
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentTopicBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.familiar.shaketopic.ShakeTopicViewModel
import com.teumteum.teumteum.util.custom.dialog.CommonDialogConfig
import com.teumteum.teumteum.util.custom.dialog.CommonDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopicFragment :
    BindingFragment<FragmentTopicBinding>(R.layout.fragment_topic), AppBarLayout {

    private val topicAdapter = TopicAdapter()
    private val viewModel by viewModels<ShakeTopicViewModel>({ requireActivity() }) //requireParentFragment
    private val visitedPages = HashSet<Int>()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) { //바인딩 객체 초기화 에러 onCreated -> onViewCreated로 바꾸니까 해결됨
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        setUpObserver()
        initAppBarLayout()
    }

    private fun setUpObserver() {
        viewModel.topics.observe(requireActivity()) { topics ->
            if (topics.isNotEmpty()) {
                updateViewPagerWithApiData(topics)
            }
        }
    }

    private fun updateViewPagerWithApiData(newApiDataList: List<TopicResponse>) {
        topicAdapter.submitList(newApiDataList)
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)
        setAppBarBackgroundColor(com.teumteum.base.R.color.background)
        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_close,
                useRippleEffect = false,
                clickEvent = ::initDialog
            )
        )
    }

    private fun initDialog() {
        CommonDialogFragment.newInstance(
            commonDialogConfig = CommonDialogConfig(
                title = getString(R.string.familiar_exit_dialog_title),
                description = getString(R.string.familiar_exit_dialog_description),
                positiveButtonText = getString(R.string.exit)
            ),
            onPositiveButtonClicked = ::startMainActivity
        ).show(childFragmentManager, "CommonDialogFragmentTag")
    }

    private fun startMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
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