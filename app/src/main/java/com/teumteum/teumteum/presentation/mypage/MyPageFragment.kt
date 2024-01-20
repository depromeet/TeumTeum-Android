package com.teumteum.teumteum.presentation.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentMyPageBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.SettingViewModel


class MyPageFragment :
    BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel: SettingViewModel by activityViewModels()
    private val myPageViewModel: MyPageViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.settingStatus.collect { status ->
                handleSettingStatus(status)
            }
        }

        binding.composeMypage.setContent {
            MyPageScreen(viewModel = viewModel, myPageViewModel = myPageViewModel)
        }
    }

    private fun handleSettingStatus(status: SettingStatus) {
        when (status) {
            SettingStatus.SETTING -> {
                findNavController().navigate(R.id.action_fragment_my_page_to_fragment_setting)
                    (activity as MainActivity).hideBottomNavi()
            }
            SettingStatus.RECOMMEND -> {
                findNavController().navigate(R.id.action_fragment_my_page_to_fragment_recommend)
                    (activity as MainActivity).hideBottomNavi()
            }
            else -> {}
        }
    }

    companion object {
    }
}