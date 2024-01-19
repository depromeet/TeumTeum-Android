package com.teumteum.teumteum.presentation.mypage.setting.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentSignoutBinding
import com.teumteum.teumteum.presentation.mypage.setting.SettingSignOutScreen
import com.teumteum.teumteum.presentation.mypage.setting.SettingViewModel

class SignOutFragment: BindingFragment<FragmentSignoutBinding>(R.layout.fragment_signout) {
    private val viewModel: SettingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeSignout.setContent {
            SettingSignOutScreen(viewModel)
        }

    }

    companion object {
    }
}