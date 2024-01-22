package com.teumteum.teumteum.presentation.mypage.editCard

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentEditCardBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel

class EditCardFragment: BindingFragment<FragmentEditCardBinding>(R.layout.fragment_edit_card) {
    private val viewModel: SettingViewModel by activityViewModels()
    private val myPageViewModel: MyPageViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        (activity as MainActivity).hideBottomNavi()

        lifecycleScope.launchWhenStarted {
            viewModel.settingStatus.collect { status ->
                handleSettingStatus(status)
            }
        }

        binding.composeEditCard.setContent {
            EditCardScreen(myPageViewModel, viewModel, navController)
        }

    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (activity as MainActivity).showBottomNavi()
        }
    }



    private fun handleSettingStatus(status: SettingStatus) {
        when (status) {
            else -> {}
        }
    }

    companion object {
    }

}