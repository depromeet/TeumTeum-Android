package com.teumteum.teumteum.presentation.mypage

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentMyPageBinding


class MyPageFragment :
    BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeMypage.setContent {
            MyPageScreen()
        }

    }

    fun goSettingScreen() {
    }



    companion object {
    }
}