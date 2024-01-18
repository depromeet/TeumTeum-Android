package com.teumteum.teumteum.presentation.group.join

import android.content.Intent
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
import com.teumteum.teumteum.databinding.ActivityGroupMeetCheckBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GroupMeetCheckActivity :
    BindingActivity<ActivityGroupMeetCheckBinding>(R.layout.activity_group_meet_check),
    AppBarLayout {

    private val viewModel by viewModels<GroupMeetCheckViewModel>()

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

    private fun initEvent() {
        binding.checkboxWaring.setOnCheckedChangeListener { _, isCheck ->
            binding.btnJoin.isEnabled = isCheck
        }

        binding.btnJoin.setOnClickListener {
            // TODO 해당 그룹에 아이디로 변경해야함
            viewModel.joinGroup(0L)
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
                        toast(it.msg)
                    }

                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }
}