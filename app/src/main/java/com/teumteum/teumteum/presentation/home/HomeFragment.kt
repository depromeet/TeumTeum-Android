package com.teumteum.teumteum.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.toast
import com.teumteum.domain.enumSet.EnumTopic
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentHomeBinding
import com.teumteum.teumteum.presentation.group.GroupListActivity
import com.teumteum.teumteum.presentation.group.GroupListAdapter
import com.teumteum.teumteum.presentation.group.GroupListUiState
import com.teumteum.teumteum.presentation.group.GroupListViewModel
import com.teumteum.teumteum.presentation.group.search.SearchActivity
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
        binding.floatingBtn.setOnClickListener { navigateToMoimFragment() }
        initView()
        initEvent()
        observe()
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)
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
        addMenuToRight(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_search,
                useRippleEffect = false,
                clickEvent = {
                    Intent(requireActivity(), SearchActivity::class.java).apply {
                        startActivity(this)
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

        }
        binding.rvRecommendMeet.adapter = adapter
        binding.tvRecommendTitle.text =
            getString(R.string.home_recommend_meet_title, viewModel.getLocation().split(" ").last())
    }

    private fun initEvent() {
        binding.cardConcerned.setOnClickListener {
            goToGroupListActivity(EnumTopic.CONCERNED.topic, GroupListActivity.TOPIC)
        }

        binding.cardProject.setOnClickListener {
            goToGroupListActivity(EnumTopic.PROJECT.topic, GroupListActivity.TOPIC)
        }

        binding.cardStudy.setOnClickListener {
            goToGroupListActivity(EnumTopic.STUDY.topic, GroupListActivity.TOPIC)
        }

        binding.cardWork.setOnClickListener {
            goToGroupListActivity(EnumTopic.WORK.topic, GroupListActivity.TOPIC)
        }

        binding.tvRecommendMore.setOnClickListener {
            goToGroupListActivity(viewModel.getLocation(), GroupListActivity.LOCATION)
        }
    }

    private fun observe() {
        viewModel.groupData.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is GroupListUiState.SetMeetings -> {
                        adapter?.setItems(it.data)
                    }

                    is GroupListUiState.Failure -> {
                        requireActivity().toast(it.msg)
                    }

                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun goToGroupListActivity(title: String, type: Int) {
        startActivity(GroupListActivity.getIntent(requireActivity(), title, type))
    }

    private fun navigateToMoimFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_moimFragment)
    }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }
}