package com.teumteum.teumteum.presentation.group

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.intExtra
import com.teumteum.base.util.extension.stringExtra
import com.teumteum.base.util.extension.toast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityGroupListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GroupListActivity : BindingActivity<ActivityGroupListBinding>(R.layout.activity_group_list) {
    private lateinit var adapter: GroupListAdapter
    private val viewModel by viewModels<GroupListViewModel>()
    private val title by stringExtra()
    private val type by intExtra()

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
            Toast.makeText(this, it.photoUrls.first(), Toast.LENGTH_SHORT).show()
        }
        binding.rvGroupList.adapter = adapter
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
                    is GroupListUiState.Success -> {
                        adapter.setItems(it.data)
                    }

                    is GroupListUiState.Failure -> {
                        toast(it.msg)
                    }

                    else -> {}
                }

            }.launchIn(lifecycleScope)
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