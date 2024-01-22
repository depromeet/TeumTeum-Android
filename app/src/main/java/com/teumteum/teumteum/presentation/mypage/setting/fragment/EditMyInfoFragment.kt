package com.teumteum.teumteum.presentation.mypage.setting.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentEditMyinfoBinding
import com.teumteum.teumteum.presentation.mypage.setting.EditMyInfoScreen
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel

class EditMyInfoFragment: BindingFragment<FragmentEditMyinfoBinding>(R.layout.fragment_edit_myinfo) {
    private val viewModel : SettingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        binding.composeEditMyinfo.setContent {
            EditMyInfoScreen(viewModel, navController)
        }
    }

    companion object {
    }
}