package com.teumteum.teumteum.presentation.mypage.recommend.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentServiceBinding
import com.teumteum.teumteum.presentation.mypage.setting.SettingServiceScreen
import com.teumteum.teumteum.presentation.mypage.setting.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.SettingViewModel

class ServiceFragment: BindingFragment<FragmentServiceBinding>(R.layout.fragment_service) {
    private val viewModel: SettingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeService.setContent {
            SettingServiceScreen()
        }
    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.updateSettingStatus(SettingStatus.SETTING)
        }
    }

    companion object {
    }
}