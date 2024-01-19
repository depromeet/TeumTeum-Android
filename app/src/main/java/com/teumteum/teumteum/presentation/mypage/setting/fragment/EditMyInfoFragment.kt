package com.teumteum.teumteum.presentation.mypage.setting.fragment

import android.os.Bundle
import android.view.View
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentEditMyinfoBinding
import com.teumteum.teumteum.presentation.mypage.setting.EditMyInfoScreen

class EditMyInfoFragment: BindingFragment<FragmentEditMyinfoBinding>(R.layout.fragment_edit_myinfo) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeEditMyinfo.setContent {
            EditMyInfoScreen()
        }

    }

    companion object {
    }
}