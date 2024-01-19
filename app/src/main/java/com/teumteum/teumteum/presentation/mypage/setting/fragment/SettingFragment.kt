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
            goMyPageScreen()
        }
    }

    fun goMyPageScreen() {
        findNavController().navigate(R.id.action_fragment_moim_to_fragment_home)
            (activity as MainActivity).showBottomNavi()
    }

    private fun handleSettingStatus(status: SettingStatus) {
        when (status) {
            SettingStatus.NOTION -> {
                // 개인정보 약관 페이지로 이동

            }
            SettingStatus.LOGOUT -> {
                // Sign out 페이지로 이동
//                findNavController().navigate(R.id.action_settingFragment_to_signOutFragment)
            }
            SettingStatus.SIGNOUT ->  {

            }
            SettingStatus.DEFAULT -> {
                goMyPageScreen()
            }
            SettingStatus.EDIT -> {

            }
            else -> {}
        }
    }

    companion object {
    }

}