package com.teumteum.teumteum.presentation.group.join

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.defaultSnackBar
import com.teumteum.base.util.extension.longExtra
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityGroupMeetCheckBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GroupMeetCheckActivity :
    BindingActivity<ActivityGroupMeetCheckBinding>(R.layout.activity_group_meet_check),
    AppBarLayout {

    private val viewModel by viewModels<GroupMeetCheckViewModel>()
    private val meetingId by longExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()
        initEvent()
        observe()
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)

        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_arrow_left_l,
                useRippleEffect = false,
                clickEvent = {
                    finish()
                }
            )
        )
    }

    private fun initEvent() {
        binding.checkboxWaring.setOnCheckedChangeListener { _, isCheck ->
            binding.btnJoin.isEnabled = isCheck
        }

        binding.btnJoin.setOnClickListener {
            viewModel.joinGroup(meetingId)
        }
    }

    private fun observe() {
        viewModel.joinState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is MeetCheckUiState.Success -> {
                        startActivity(GroupMeetCompleteActivity.getIntent(this, it.data.id))
                    }
                    is MeetCheckUiState.Failure -> {
                        defaultSnackBar(binding.root, it.msg)
                    }

                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }

    companion object {
        fun getIntent(context: Context, meetingId: Long) =
            Intent(context, GroupMeetCheckActivity::class.java).apply {
                putExtra("meetingId", meetingId)
            }
    }
}