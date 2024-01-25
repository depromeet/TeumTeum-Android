package com.teumteum.teumteum.presentation.group.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.defaultToast
import com.teumteum.base.util.extension.hideKeyboard
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySearchBinding
import com.teumteum.teumteum.presentation.group.GroupListAdapter
import com.teumteum.teumteum.presentation.group.join.GroupDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchActivity : BindingActivity<ActivitySearchBinding>(R.layout.activity_search) {
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var keywordAdapter: KeywordAdapter
    private lateinit var groupListAdapter: GroupListAdapter
    private var isScrolled: Boolean = false

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
            startActivity(GroupDetailActivity.getIntent(this, it.id))
            openActivitySlideAnimation()
        }
        binding.rvGroupList.adapter = groupListAdapter
        infinityScroll()
    }

    private fun initEvent() {
        binding.ivSearch.setOnClickListener {
            if (viewModel.isInputBlank) {
                defaultToast(getString(R.string.group_search_empty_keyword))
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

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun observe() {
        viewModel.searchData.flowWithLifecycle(lifecycle)
            .onEach {
                binding.clRecommendKeyword.isVisible = it is SearchUiState.Init
                binding.rvGroupList.isGone = it is SearchUiState.Init || it is SearchUiState.Empty
                binding.clEmpty.isVisible = it is SearchUiState.Empty
                when (it) {
                    is SearchUiState.SetMeetings -> {
                        groupListAdapter.setItems(it.data)
                    }

                    is SearchUiState.AddMeetings -> {
                        groupListAdapter.addItems(it.data)
                    }

                    is SearchUiState.Empty -> {
                        binding.tvEmptyGroupTitle.text =
                            getString(R.string.group_search_keyword, it.keyword)
                    }

                    is SearchUiState.Failure -> {
                        defaultToast(it.msg)
                    }

                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }

    private fun infinityScroll() {
        binding.rvGroupList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && !binding.rvGroupList.canScrollVertically(1) && (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == groupListAdapter.itemCount - 1) {
                    viewModel.getSearchGroup()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !isScrolled) {
                    isScrolled = true
                }
            }
        })
    }

    override fun finish() {
        super.finish()
        closeActivitySlideAnimation()
    }
}