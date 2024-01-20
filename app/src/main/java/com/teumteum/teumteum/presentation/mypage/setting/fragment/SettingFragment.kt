package com.teumteum.teumteum.presentation.mypage.setting.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentSettingBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.SettingScreen
import com.teumteum.teumteum.presentation.mypage.setting.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.SettingViewModel

class SettingFragment: BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    private val viewModel: SettingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.settingStatus.collect { status ->
                handleSettingStatus(status)
            }
        }

        binding.composeSetting.setContent {
            SettingScreen(viewModel)
        }

    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.updateSettingStatus(SettingStatus.DEFAULT)
        }
    }

    fun goMyPageScreen() {
        findNavController().popBackStack()
            (activity as MainActivity).showBottomNavi()
    }

    private fun handleSettingStatus(status: SettingStatus) {
        when (status) {
            SettingStatus.NOTION -> {
                findNavController().navigate(R.id.action_fragment_setting_to_fragment_service)
                (activity as MainActivity).hideBottomNavi()
            }
            SettingStatus.LOGOUT -> {
                viewModel.handleDialogChange(status)
            }
            SettingStatus.LOGOUT_CONFIRM -> {
                // 로그인 화면 이동
                viewModel.updateSettingStatus(SettingStatus.DEFAULT)
            }
            SettingStatus.SIGNOUT ->  {
                findNavController().navigate(R.id.action_fragment_setting_to_fragment_signout)
                    (activity as MainActivity).hideBottomNavi()
            }
            SettingStatus.DEFAULT -> {
                goMyPageScreen()
            }
            SettingStatus.EDIT -> {
                findNavController().navigate(R.id.action_fragment_setting_to_fragment_edit_myinfo)
                (activity as MainActivity).hideBottomNavi()
            }
            else -> {}
        }
    }

    companion object {
    }

}