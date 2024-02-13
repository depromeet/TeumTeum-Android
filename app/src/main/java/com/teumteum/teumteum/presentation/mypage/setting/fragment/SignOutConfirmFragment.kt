package com.teumteum.teumteum.presentation.mypage.setting.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.teumteum.teumteum.presentation.mypage.setting.SettingSignOutConfirm
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel
import com.teumteum.teumteum.presentation.signin.SignInActivity
import timber.log.Timber

class SignOutConfirmFragment : BindingFragment<FragmentSignoutBinding>(R.layout.fragment_signout) {
    private val viewModel: SettingViewModel by activityViewModels()
    private val myPageViewModel: MyPageViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.settingStatus.collect { status ->
                handleSettingStatus(status)
            }
        }

        (activity as MainActivity).hideBottomNavi()
        val navController = findNavController()

        binding.composeSignout.setContent {
            CompositionLocalProvider(TmtmColorPalette provides if (isSystemInDarkTheme()) ColorPalette_Dark else ColorPalette_Light) {
                SettingSignOutConfirm(
                    viewModel = viewModel,
                    myPageViewModel = myPageViewModel,
                    navController = navController
                )
            }
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
            SettingStatus.SIGNOUT -> {
                (activity as MainActivity).hideBottomNavi()
                navigateToSignInActivity()
                viewModel.updateSettingStatus(SettingStatus.DEFAULT)
            }

            else -> {}
        }
    }

    companion object {
    }
}