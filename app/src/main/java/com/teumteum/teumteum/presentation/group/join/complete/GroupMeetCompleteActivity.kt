package com.teumteum.teumteum.presentation.group.join.complete

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
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityGroupMeetCompleteBinding
import com.teumteum.teumteum.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GroupMeetCompleteActivity :
    BindingActivity<ActivityGroupMeetCompleteBinding>(R.layout.activity_group_meet_complete),
    AppBarLayout {

    private val viewModel by viewModels<GroupMeetCompleteViewModel>()
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
                    closeActivitySlideAnimation()
                }
            )
        )
    }

    private fun observe() {
        viewModel.cancelState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is GroupMeetCancelUiState.Success -> {
                        defaultToast(getString(R.string.group_meet_cancel))
                        goToMainActivity()
                        finish()
                    }

                    is GroupMeetCancelUiState.Failure -> {
                        defaultToast(it.msg)
                    }

                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }

    private fun initEvent() {
        binding.btnCancel.setOnClickListener {
            viewModel.deleteJoinCancel(meetingId)
        }

        binding.btnHome.setOnClickListener {
            goToMainActivity()
            finish()
        }
    }

    private fun goToMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(this)
        }
        openActivitySlideAnimation()
    }

    companion object {
        fun getIntent(context: Context, meetingId: Long) =
            Intent(context, GroupMeetCompleteActivity::class.java).apply {
                putExtra("meetingId", meetingId)
            }
    }
}