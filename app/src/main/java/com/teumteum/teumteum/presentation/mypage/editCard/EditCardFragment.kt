package com.teumteum.teumteum.presentation.mypage.editCard

import android.content.Intent
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
import com.teumteum.teumteum.presentation.mypage.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.SettingViewModel

class EditCardFragment: BindingFragment<FragmentEditCardBinding>(R.layout.fragment_edit_card) {
    private val viewModel: SettingViewModel by activityViewModels()
    private val myPageViewModel: MyPageViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.settingStatus.collect { status ->
                handleSettingStatus(status)
            }
        }

        binding.composeEditCard.setContent {
            EditCardScreen(myPageViewModel, viewModel)
        }

    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.updateSettingStatus(SettingStatus.DEFAULT)
        }
    }

    fun goMyPageScreen() {
        findNavController().popBackStack()
        (activity as MainActivity).showBottomNavi()
    }


    private fun handleSettingStatus(status: SettingStatus) {
        when (status) {
            SettingStatus.DEFAULT -> {
                goMyPageScreen()
            }
            else -> {}
        }
    }

    companion object {
    }

}