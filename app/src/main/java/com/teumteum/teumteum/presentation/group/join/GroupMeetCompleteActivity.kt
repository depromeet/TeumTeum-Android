package com.teumteum.teumteum.presentation.group.join

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.toast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityGroupMeetCompleteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GroupMeetCompleteActivity :
    BindingActivity<ActivityGroupMeetCompleteBinding>(R.layout.activity_group_meet_complete),
    AppBarLayout {

    private val viewModel by viewModels<GroupMeetCompleteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                clickEvent = null
            )
        )
    }

    private fun observe() {
        viewModel.cancelState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is GroupMeetCancelUiState.Success -> {
                        // TODO 홈으로 가기 구현
                    }
                    is GroupMeetCancelUiState.Failure -> {
                        toast(it.msg)
                    }

                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }

    private fun initEvent() {
        binding.btnCancel.setOnClickListener {
            // TODO 모임 ID 넣어야 함
            viewModel.deleteJoinCancel(0L)
        }

        binding.btnHome.setOnClickListener {
            // TODO 홈으로 가기 구현
        }
    }
}