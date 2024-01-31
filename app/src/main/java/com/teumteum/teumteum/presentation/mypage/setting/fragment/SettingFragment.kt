package com.teumteum.teumteum.presentation.mypage.setting.fragment

import android.content.Intent
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
import com.teumteum.base.util.extension.defaultToast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentSettingBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.SettingScreen
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel
import com.teumteum.teumteum.presentation.signin.SignInActivity

class SettingFragment: BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    private val viewModel: SettingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.settingStatus.collect { status ->
                handleSettingStatus(status)
            }
        }

        val navController = findNavController()
        (activity as MainActivity).hideBottomNavi()

        binding.composeSetting.setContent {
            CompositionLocalProvider(TmtmColorPalette provides if(isSystemInDarkTheme()) ColorPalette_Dark else ColorPalette_Light ) {
                SettingScreen(viewModel, navController)
            }
        }

    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (activity as MainActivity).showBottomNavi()
        }
    }

    private fun navigateToSignInActivity() {
        val intent = Intent(requireContext(), SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }

    private fun handleSettingStatus(status: SettingStatus) {
        when (status) {
            SettingStatus.LOGOUT -> {
                viewModel.handleDialogChange(status)
            }
            SettingStatus.LOGOUT_CONFIRM -> {
                (activity as MainActivity).hideBottomNavi()
                navigateToSignInActivity()
                viewModel.updateSettingStatus(SettingStatus.DEFAULT)
            }
            SettingStatus.ERROR -> {
                requireActivity().defaultToast("서버 통신 에러")
            }
            SettingStatus.OPENSOURCE -> {

            }
            else -> {}
        }
    }

    companion object {
    }

}