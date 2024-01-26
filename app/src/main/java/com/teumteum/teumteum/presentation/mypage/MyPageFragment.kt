package com.teumteum.teumteum.presentation.mypage

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.compose.theme.ColorPalette_Dark
import com.teumteum.base.component.compose.theme.ColorPalette_Light
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentMyPageBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.DialogEvent
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel


class MyPageFragment :
    BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel: SettingViewModel by activityViewModels()
    private val myPageViewModel: MyPageViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        (activity as MainActivity).showBottomNavi()

        binding.composeMypage.setContent {
            CompositionLocalProvider(TmtmColorPalette provides if(isSystemInDarkTheme()) ColorPalette_Dark else ColorPalette_Light ) {
                MyPageScreen(navController = navController, viewModel = viewModel, myPageViewModel = myPageViewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        myPageViewModel.loadUserInfo()
        viewModel.getUserOpenMeeting()
        viewModel.getUserClosedMeeting()
    }

    companion object {
    }
}