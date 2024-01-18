package com.teumteum.teumteum.presentation.mypage.setting

import android.os.Bundle
import android.view.View
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentSignoutBinding

class SignOutFragment: BindingFragment<FragmentSignoutBinding>(R.layout.fragment_signout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeSignout.setContent {
            SettingSignOutScreen()
        }

    }

    companion object {
    }
}