package com.teumteum.teumteum.presentation.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
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

        val navController = findNavController()

        binding.composeMypage.setContent {
            MyPageScreen(navController = navController, viewModel = viewModel, myPageViewModel = myPageViewModel)
        }
    }



    companion object {
    }
}