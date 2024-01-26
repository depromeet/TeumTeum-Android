package com.teumteum.teumteum.presentation.mypage.setting.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.compose.theme.ColorPalette_Dark
import com.teumteum.base.component.compose.theme.ColorPalette_Light
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentSignoutBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.SettingSignOutScreen
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel

class SignOutFragment: BindingFragment<FragmentSignoutBinding>(R.layout.fragment_signout) {
    private val viewModel: SettingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        (activity as MainActivity).hideBottomNavi()

        binding.composeSignout.setContent {
            CompositionLocalProvider(TmtmColorPalette provides if(isSystemInDarkTheme()) ColorPalette_Dark else ColorPalette_Light ) {
                SettingSignOutScreen(viewModel, navController)
            }
        }

    }


    companion object {
    }
}