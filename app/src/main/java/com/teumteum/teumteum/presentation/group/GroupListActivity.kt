package com.teumteum.teumteum.presentation.group

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.intExtra
import com.teumteum.base.util.extension.stringExtra
import com.teumteum.base.util.extension.toast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityGroupListBinding
import com.teumteum.teumteum.presentation.group.join.GroupDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GroupListActivity : BindingActivity<ActivityGroupListBinding>(R.layout.activity_group_list) {
    private lateinit var adapter: GroupListAdapter
    private val viewModel by viewModels<GroupListViewModel>()
    private val title by stringExtra()
    private val type by intExtra()
    private var isScrolled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initEvent()
        observe()
    }

    private fun initView() {
        title?.let {
            binding.tvTitle.text = it.replace("_", " ")
            if (type == LOCATION) {
                viewModel.initCurrentPage(location = title)
            } else if (type == TOPIC) {
                viewModel.initCurrentPage(topic = title)
            }
        }

        adapter = GroupListAdapter {
            startActivity(GroupDetailActivity.getIntent(this, it.id))
        }

        binding.rvGroupList.adapter = adapter
        infinityScroll()
    }

    private fun initEvent() {
        binding.ivClose.setOnClickListener {
            finish()
        }
    }

    private fun observe() {
        viewModel.groupData.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is GroupListUiState.SetMeetings -> {
                        adapter.setItems(it.data)
                    }

                    is GroupListUiState.AddMeetings -> {
                        adapter.addItems(it.data)
                    }

                    is GroupListUiState.Failure -> {
                        toast(it.msg)
                    }

                    else -> {}
                }

            }.launchIn(lifecycleScope)
    }

    private fun infinityScroll() {
        binding.rvGroupList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && !binding.rvGroupList.canScrollVertically(1) && (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition() == adapter.itemCount - 1) {
                    if (type == LOCATION) {
                        viewModel.getGroupList(location = title)
                    } else if (type == TOPIC) {
                        viewModel.getGroupList(topic = title)
                    }
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

    companion object {
        fun getIntent(context: Context, title: String, type: Int) =
            Intent(context, GroupListActivity::class.java).apply {
                putExtra("title", title)
                putExtra("type", type)
            }

        const val LOCATION = 0
        const val TOPIC = 1
    }
}