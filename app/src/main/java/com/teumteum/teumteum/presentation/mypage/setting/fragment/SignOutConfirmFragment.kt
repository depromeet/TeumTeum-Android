package com.teumteum.teumteum.presentation.mypage.setting.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentSignoutBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.SettingSignOutConfirm
import com.teumteum.teumteum.presentation.mypage.setting.SettingSignOutScreen
import com.teumteum.teumteum.presentation.mypage.setting.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.SettingViewModel
import com.teumteum.teumteum.presentation.signin.SignInActivity

class SignOutConfirmFragment: BindingFragment<FragmentSignoutBinding>(R.layout.fragment_signout) {
    private val viewModel: SettingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.settingStatus.collect { status ->
                handleSettingStatus(status)
            }
        }

        binding.composeSignout.setContent {
            SettingSignOutConfirm(viewModel = viewModel)
        }

    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.updateSettingStatus(SettingStatus.SIGNOUT)

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
                findNavController().popBackStack()
            }
            SettingStatus.SIGNOUT_CONFIRM -> {
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