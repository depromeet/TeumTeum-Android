package com.teumteum.teumteum.presentation.group.search

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingActivity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySearchBinding
import com.teumteum.teumteum.presentation.group.GroupListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchActivity: BindingActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var keywordAdapter: keywordAdapter
    private lateinit var groupListAdapter: GroupListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        initView()
        observe()
    }

    private fun  initView() {
        keywordAdapter = keywordAdapter {

        }
        binding.rvRecommendKeyword.adapter = keywordAdapter
        keywordAdapter.setItems(viewModel.keywordList)

        groupListAdapter = GroupListAdapter {

        }
        binding.rvGroupList.adapter = groupListAdapter
    }

    @OptIn(FlowPreview::class)
    private fun observe() {
        viewModel.searchData.flowWithLifecycle(lifecycle)
            .onEach {
                binding.clRecommendKeyword.isVisible = it is SearchUiState.Init
                when(it) {
                    is SearchUiState.Success -> {
                        groupListAdapter.setItems(it.data)
                    }
                    else -> {}
                }
            }.launchIn(lifecycleScope)

        viewModel.etSearch.flowWithLifecycle(lifecycle)
            .debounce(1000)
            .onEach {
                if (it.isNotEmpty()) {
                    viewModel.getSearchGroup(it)
                }
            }.launchIn(lifecycleScope)
    }
}