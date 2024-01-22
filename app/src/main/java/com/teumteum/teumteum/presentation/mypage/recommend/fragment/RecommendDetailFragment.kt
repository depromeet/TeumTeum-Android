package com.teumteum.teumteum.presentation.mypage.recommend.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.base.util.extension.toast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentRecommendDetailBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.recommend.RecommendDetailScreen
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel

class RecommendDetailFragment: BindingFragment<FragmentRecommendDetailBinding>(R.layout.fragment_recommend_detail) {
    private val viewModel: SettingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).hideBottomNavi()

        lifecycleScope.launchWhenStarted {
            viewModel.settingStatus.collect { status ->
                handleSettingStatus(status)
            }
        }

        binding.composeRecommendDetail.setContent {
            RecommendDetailScreen()
        }

    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.updateSettingStatus(SettingStatus.RECOMMEND)
        }
    }
    private fun handleSettingStatus(status: SettingStatus) {
        when (status) {
            SettingStatus.ERROR -> {
                requireActivity().toast("서버 통신에 실패했습니다")
                viewModel.updateSettingStatus(SettingStatus.DEFAULT)
            }
            else -> {}
        }
    }

    companion object {
    }
}