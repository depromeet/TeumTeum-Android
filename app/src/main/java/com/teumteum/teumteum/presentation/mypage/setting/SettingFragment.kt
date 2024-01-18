package com.teumteum.teumteum.presentation.mypage.setting

import android.os.Bundle
import android.view.View
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentSettingBinding
import com.teumteum.teumteum.presentation.mypage.MyPageScreen

class SettingFragment: BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeSetting.setContent {
            SettingScreen()
        }

    }

    fun goMyPageScreen() {

    }

    companion object {
    }

}