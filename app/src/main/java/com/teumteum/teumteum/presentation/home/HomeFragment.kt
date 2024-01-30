package com.teumteum.teumteum.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingActivity
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.defaultToast
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.domain.enumSet.EnumTopic
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentHomeBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.group.GroupListActivity
import com.teumteum.teumteum.presentation.group.GroupListAdapter
import com.teumteum.teumteum.presentation.group.GroupListUiState
import com.teumteum.teumteum.presentation.group.GroupListViewModel
import com.teumteum.teumteum.presentation.group.join.GroupDetailActivity
import com.teumteum.teumteum.presentation.group.search.SearchActivity
import com.teumteum.teumteum.util.callback.CustomBackPressedCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment :
    BindingFragment<FragmentHomeBinding>(R.layout.fragment_home), AppBarLayout {
    private val viewModel by viewModels<GroupListViewModel>()
    private var adapter: GroupListAdapter? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAppBarLayout()
        binding.floatingBtn.setOnSingleClickListener { navigateToMoimFragment() }
        initView()
        initEvent()
        observe()
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)
        (activity as MainActivity).showBottomNavi()
        setAppBarBackgroundColor(com.teumteum.base.R.color.background)
        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_logo_title,
                useRippleEffect = false,
                clickEvent = {
                    binding.scrollView.smoothScrollTo(0,0)
                }
            )
        )

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, CustomBackPressedCallback(requireActivity(), getString(R.string.alert_back_pressed_finish)))

        addMenuToRight(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_search,
                useRippleEffect = false,
                clickEvent = {
                    Intent(requireActivity(), SearchActivity::class.java).apply {
                        startActivity(this)
                        (activity as? BindingActivity<*>)?.openActivitySlideAnimation()
                    }
                }
            ),
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_bell,
                useRippleEffect = false,
                clickEvent = null
            )
        )
    }

    private fun initView() {
        viewModel.initCurrentPage(viewModel.getLocation())
        adapter = GroupListAdapter {
            startActivity(GroupDetailActivity.getIntent(requireActivity(), it.id))
            (activity as? BindingActivity<*>)?.openActivitySlideAnimation()
        }
        binding.rvRecommendMeet.adapter = adapter
        binding.tvRecommendTitle.text =
            getString(R.string.home_recommend_meet_title, viewModel.getLocation().split(" ").last())
    }

    private fun initEvent() {
        binding.cardConcerned.setOnSingleClickListener {
            goToGroupListActivity(EnumTopic.CONCERNED.topic, GroupListActivity.TOPIC)
        }

        binding.cardProject.setOnSingleClickListener {
            goToGroupListActivity(EnumTopic.PROJECT.topic, GroupListActivity.TOPIC)
        }

        binding.cardStudy.setOnSingleClickListener {
            goToGroupListActivity(EnumTopic.STUDY.topic, GroupListActivity.TOPIC)
        }

        binding.cardWork.setOnSingleClickListener {
            goToGroupListActivity(EnumTopic.WORK.topic, GroupListActivity.TOPIC)
        }

        binding.tvRecommendMore.setOnSingleClickListener {
            goToGroupListActivity(viewModel.getLocation(), GroupListActivity.LOCATION)
        }
    }

    private fun observe() {
        viewModel.groupData.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                binding.tvGroupEmpty.isVisible = it is GroupListUiState.Empty
                binding.rvRecommendMeet.isVisible = it !is GroupListUiState.Empty
                when (it) {
                    is GroupListUiState.SetMeetings -> {
                        adapter?.setItems(it.data)
                    }

                    is GroupListUiState.Failure -> {
                        requireActivity().defaultToast(it.msg)
                    }

                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun goToGroupListActivity(title: String, type: Int) {
        startActivity(GroupListActivity.getIntent(requireActivity(), title, type))
        (activity as? BindingActivity<*>)?.openActivitySlideAnimation()
    }

    private fun navigateToMoimFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_moimFragment)
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}