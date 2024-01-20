package com.teumteum.teumteum.presentation.mypage.recommend.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentRecommendBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.recommend.RecommendScreen
import com.teumteum.teumteum.presentation.mypage.setting.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.SettingViewModel

class RecommendFragment: BindingFragment<FragmentRecommendBinding>(R.layout.fragment_recommend) {
    private val viewModel: SettingViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        (activity as MainActivity).hideBottomNavi()

        binding.composeRecommend.setContent {
            RecommendScreen(viewModel, navController)
        }

    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (activity as MainActivity).showBottomNavi()
        }
    }

    fun goMyPageScreen() {
        findNavController().popBackStack()
        (activity as MainActivity).showBottomNavi()
    }


    companion object {
    }
}