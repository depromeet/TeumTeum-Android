package com.teumteum.teumteum.presentation.moim

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.compose.theme.ColorPalette_Dark
import com.teumteum.base.component.compose.theme.ColorPalette_Light
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.base.util.extension.defaultToast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentModifyMoimBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.moim.compose.MoimModify
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MoimModifyFragment: BindingFragment<FragmentModifyMoimBinding>(R.layout.fragment_modify_moim){
    private val viewModel: MoimViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).hideBottomNavi()

        observe()

        binding.composeModifyMoim.setContent {
            CompositionLocalProvider(TmtmColorPalette provides if(isSystemInDarkTheme()) ColorPalette_Dark else ColorPalette_Light ) {
                MoimModify()
            }
        }
    }

    private fun observe() {
        viewModel.screenState.flowWithLifecycle(lifecycle)
            .onEach {
                when(it) {
                    ScreenState.Failure -> { context?.defaultToast(getString(R.string.setting_dialog_moim_delete_failure)) }
                    ScreenState.Server -> { context?.defaultToast(getString(R.string.moim_alert_message_server)) }
                    ScreenState.DeleteSuccess -> {context?.defaultToast(getString(R.string.setting_dialog_moim_delete_success))}
                    else -> {}
                }
            }
            .launchIn(lifecycleScope)
    }
}