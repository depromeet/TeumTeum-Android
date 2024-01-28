package com.teumteum.teumteum.presentation.group.join

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.compose.theme.ColorPalette_Dark
import com.teumteum.base.component.compose.theme.ColorPalette_Light
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.base.util.extension.defaultToast
import com.teumteum.base.util.extension.longExtra
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityGroupDetailBinding
import com.teumteum.teumteum.presentation.moim.compose.MoimConfirm
import com.teumteum.teumteum.presentation.moim.MoimViewModel
import com.teumteum.teumteum.presentation.moim.ScreenState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class GroupDetailActivity :
    BindingActivity<ActivityGroupDetailBinding>(R.layout.activity_group_detail) {
    private val meetingId by longExtra()
    private val viewModel: MoimViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.composeGroup.setContent {
            CompositionLocalProvider(TmtmColorPalette provides if(isSystemInDarkTheme()) ColorPalette_Dark else ColorPalette_Light ) {
                MoimConfirm(viewModel = viewModel, activity = this, isJoinView = true, meetingId = meetingId)
            }
        }
        observe()
        initView()
    }

    private fun observe() {
        viewModel.screenState.flowWithLifecycle(lifecycle)
            .onEach {
                when(it){
                    ScreenState.Failure -> { this@GroupDetailActivity?.defaultToast(getString(R.string.moim_alert_message_failure)) }
                    ScreenState.Server -> { this@GroupDetailActivity?.defaultToast(getString(R.string.moim_alert_message_server)) }
                    ScreenState.CancelSuccess -> {
                        this@GroupDetailActivity?.defaultToast(getString(R.string.moim_alert_message_success))
                        initView()
                    }
                    ScreenState.DeleteSuccess -> {
                        finish()

                    }
                    else -> {}
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun initView() {
        viewModel.getGroup(meetingId)
    }

    override fun finish() {
        super.finish()
        closeActivitySlideAnimation()
    }

    companion object {
        fun getIntent(context: Context, meetingId: Long) =
            Intent(context, GroupDetailActivity::class.java).apply {
                putExtra("meetingId", meetingId)
            }
    }
}