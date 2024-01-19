package com.teumteum.teumteum.presentation.mypage.recommend.fragment

import android.os.Bundle
import android.view.View
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentServiceBinding
import com.teumteum.teumteum.presentation.mypage.setting.SettingServiceScreen

class ServiceFragment: BindingFragment<FragmentServiceBinding>(R.layout.fragment_service) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeService.setContent {
            SettingServiceScreen()
        }
    }

    companion object {
    }
}