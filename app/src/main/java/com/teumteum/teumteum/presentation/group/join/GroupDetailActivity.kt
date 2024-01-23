package com.teumteum.teumteum.presentation.group.join

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.longExtra
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityGroupDetailBinding
import com.teumteum.teumteum.presentation.moim.MoimConfirm
import com.teumteum.teumteum.presentation.moim.MoimViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupDetailActivity :
    BindingActivity<ActivityGroupDetailBinding>(R.layout.activity_group_detail) {
    private val meetingId by longExtra()
    private val viewModel: MoimViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.composeGroup.setContent {
            MoimConfirm(viewModel = viewModel, activity = this, isJoinView = true)
        }

        initView()
    }

    private fun initView() {
        viewModel.getGroup(meetingId)
    }

    companion object {
        fun getIntent(context: Context, meetingId: Long) =
            Intent(context, GroupDetailActivity::class.java).apply {
                putExtra("meetingId", meetingId)
            }
    }
}