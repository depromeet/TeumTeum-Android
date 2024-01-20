package com.teumteum.teumteum.presentation.mypage.recommend.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentRecommendBinding
import com.teumteum.teumteum.presentation.mypage.recommend.RecommendScreen
import com.teumteum.teumteum.presentation.mypage.setting.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.SettingViewModel

class RecommendFragment: BindingFragment<FragmentRecommendBinding>(R.layout.fragment_recommend) {
    private val viewModel: SettingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeRecommend.setContent {
            RecommendScreen()
        }

    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.updateSettingStatus(SettingStatus.DEFAULT)
        }
    }

    companion object {
    }
}