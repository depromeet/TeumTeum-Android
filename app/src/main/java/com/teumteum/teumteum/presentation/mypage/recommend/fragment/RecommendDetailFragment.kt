package com.teumteum.teumteum.presentation.mypage.recommend.fragment

import android.os.Bundle
import android.view.View
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentEditMyinfoBinding
import com.teumteum.teumteum.databinding.FragmentRecommendDetailBinding
import com.teumteum.teumteum.presentation.mypage.recommend.RecommendDetailScreen
import com.teumteum.teumteum.presentation.mypage.setting.EditMyInfoScreen

class RecommendDetailFragment: BindingFragment<FragmentRecommendDetailBinding>(R.layout.fragment_recommend_detail) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeRecommendDetail.setContent {
            RecommendDetailScreen()
        }

    }

    companion object {
    }
}