package com.teumteum.teumteum.presentation.group.join.check

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
import com.teumteum.base.util.extension.defaultToast
import com.teumteum.base.util.extension.longExtra
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityGroupMeetCheckBinding
import com.teumteum.teumteum.presentation.group.join.complete.GroupMeetCompleteActivity
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

        binding.btnJoin.setOnSingleClickListener {
            viewModel.joinGroup(meetingId)
        }
    }

    private fun observe() {
        viewModel.joinState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is MeetCheckUiState.Success -> {
                        startActivity(GroupMeetCompleteActivity.getIntent(this, it.data.id))
                        openActivitySlideAnimation()
                    }
                    is MeetCheckUiState.Failure -> {
                        defaultToast(it.msg)
                    }

                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }

    override fun finish() {
        super.finish()
        closeActivitySlideAnimation()
    }

    companion object {
        fun getIntent(context: Context, meetingId: Long) =
            Intent(context, GroupMeetCheckActivity::class.java).apply {
                putExtra("meetingId", meetingId)
            }
    }
}