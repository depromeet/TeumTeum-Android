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
import com.teumteum.teumteum.presentation.mypage.RecommendDetailScreen
import com.teumteum.teumteum.presentation.mypage.recommend.RecommendDetailViewModel
class RecommendDetailFragment: BindingFragment<FragmentRecommendDetailBinding>(R.layout.fragment_recommend_detail) {
    private val viewModel: RecommendDetailViewModel by activityViewModels()
    private var userId: Int = -1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = arguments?.getInt("id") ?: -1
        if (userId != -1) {
            viewModel.loadFriendInfo(userId.toLong())
        }

        (activity as MainActivity).showBottomNavi()
        val navController = findNavController()

        binding.composeRecommendDetail.setContent {
            RecommendDetailScreen(navController, viewModel)
        }

    }

    override fun onResume() {
        super.onResume()
//        viewModel.loadUserInfo()
    }

    companion object {
    }
}