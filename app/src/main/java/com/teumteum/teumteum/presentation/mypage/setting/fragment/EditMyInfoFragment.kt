package com.teumteum.teumteum.presentation.mypage.setting.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentEditMyinfoBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.EditMyInfoScreen
import com.teumteum.teumteum.presentation.mypage.setting.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.SettingViewModel

class EditMyInfoFragment: BindingFragment<FragmentEditMyinfoBinding>(R.layout.fragment_edit_myinfo) {
    private val viewModel : SettingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeEditMyinfo.setContent {
            EditMyInfoScreen(viewModel)
        }
    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.updateSettingStatus(SettingStatus.SETTING)
        }
    }

    private fun handleSettingStatus(status: SettingStatus) {
        when (status) {
            SettingStatus.NOTION -> {
                findNavController().navigate(R.id.action_fragment_setting_to_fragment_service)
                (activity as MainActivity).hideBottomNavi()
            }

            else -> {}
        }
    }

    companion object {
    }
}