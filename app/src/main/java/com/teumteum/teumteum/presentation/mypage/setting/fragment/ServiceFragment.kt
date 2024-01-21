package com.teumteum.teumteum.presentation.mypage.recommend.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentServiceBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.SettingServiceScreen
import com.teumteum.teumteum.presentation.mypage.setting.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.SettingViewModel

class ServiceFragment: BindingFragment<FragmentServiceBinding>(R.layout.fragment_service) {
    private val viewModel: SettingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        lifecycleScope.launchWhenStarted {
//            viewModel.settingStatus.collect { status ->
//                handleSettingStatus(status)
//            }
//        }
        val navController = findNavController()

        binding.composeService.setContent {
            SettingServiceScreen(viewModel, navController)
        }
    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (activity as MainActivity).showBottomNavi()
        }
    }

//    private fun handleSettingStatus(status: SettingStatus) {
//        when (status) {
//            SettingStatus.SETTING -> {
//                findNavController().popBackStack()
//            }
//            else -> {}
//        }
//    }

    companion object {
    }
}