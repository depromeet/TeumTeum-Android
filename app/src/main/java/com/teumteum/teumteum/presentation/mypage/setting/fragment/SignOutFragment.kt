package com.teumteum.teumteum.presentation.mypage.setting.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentSignoutBinding
import com.teumteum.teumteum.presentation.mypage.setting.SettingSignOutScreen
import com.teumteum.teumteum.presentation.mypage.setting.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.SettingViewModel

class SignOutFragment: BindingFragment<FragmentSignoutBinding>(R.layout.fragment_signout) {
    private val viewModel: SettingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.settingStatus.collect { status ->
                handleSettingStatus(status)
            }
        }

        binding.composeSignout.setContent {
            SettingSignOutScreen(viewModel)
        }

    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.updateSettingStatus(SettingStatus.SETTING)

        }
    }
    private fun handleSettingStatus(status: SettingStatus) {
        when (status) {
            SettingStatus.SETTING -> {
                findNavController().popBackStack()
            }
            else -> {}
        }
    }

    companion object {
    }
}