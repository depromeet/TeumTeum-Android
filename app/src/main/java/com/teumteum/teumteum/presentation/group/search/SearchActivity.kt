package com.teumteum.teumteum.presentation.group.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.hideKeyboard
import com.teumteum.base.util.extension.toast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySearchBinding
import com.teumteum.teumteum.presentation.group.GroupListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchActivity : BindingActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var keywordAdapter: KeywordAdapter
    private lateinit var groupListAdapter: GroupListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        initView()
        initEvent()
        observe()
    }

    private fun initView() {
        initKeywordAdapter()
        initGroupListAdapter()
    }

    private fun initKeywordAdapter() {
        keywordAdapter = KeywordAdapter {
            binding.etSearch.setText(it)
            viewModel.initCurrentPage(it)
            hideKeyboard(binding.root)
        }
        binding.rvRecommendKeyword.adapter = keywordAdapter
        keywordAdapter.setItems(viewModel.keywordList)
    }

    private fun initGroupListAdapter() {
        groupListAdapter = GroupListAdapter {
            //TODO 그룹 상세보기로 이동하는 로직 들어가야 함
        }
        binding.rvGroupList.adapter = groupListAdapter
    }

    private fun initEvent() {
        binding.ivSearch.setOnClickListener {
            if (viewModel.isInputBlank) {
                toast(getString(R.string.group_search_empty_keyword))
            } else {
                viewModel.initCurrentPage()
                hideKeyboard(binding.root)
            }
        }

        binding.ivClear.setOnClickListener {
            binding.etSearch.text.clear()
        }

        binding.etSearch.setOnFocusChangeListener { _, hasFocus ->
            binding.ivClear.isVisible = (hasFocus)
        }
    }

    private fun observe() {
        viewModel.searchData.flowWithLifecycle(lifecycle)
            .onEach {
                binding.clRecommendKeyword.isVisible = it is SearchUiState.Init
                binding.rvGroupList.isGone = it is SearchUiState.Init || it is SearchUiState.Empty
                binding.clEmpty.isVisible = it is SearchUiState.Empty
                when (it) {
                    is SearchUiState.Success -> {
                        groupListAdapter.setItems(it.data)
                    }

                    is SearchUiState.Empty -> {
                        binding.tvEmptyGroupTitle.text =
                            getString(R.string.group_search_keyword, it.keyword)
                    }

                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }
}