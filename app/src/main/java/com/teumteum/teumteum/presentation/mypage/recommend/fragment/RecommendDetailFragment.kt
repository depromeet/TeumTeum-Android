package com.teumteum.teumteum.presentation.mypage.recommend.fragment

import android.os.Bundle
import android.util.Log
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
import com.teumteum.teumteum.databinding.FragmentRecommendDetailBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.recommend.RecommendDetailScreen
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.RecommendDetailViewModel
class RecommendDetailFragment: BindingFragment<FragmentRecommendDetailBinding>(R.layout.fragment_recommend_detail) {
    private val viewModel: RecommendDetailViewModel by activityViewModels()
    private val mypageViewModel : MyPageViewModel by activityViewModels()
    private var userId: Int = -1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = arguments?.getInt("id") ?: -1
        val isJoinView = arguments?.getBoolean("isJoinView") ?: false
        if (userId != -1) {
            recommendDetailData()
        }

        (activity as MainActivity).showBottomNavi()
        val navController = findNavController()

        binding.composeRecommendDetail.setContent {
            CompositionLocalProvider(TmtmColorPalette provides if(isSystemInDarkTheme()) ColorPalette_Dark else ColorPalette_Light ) {
                RecommendDetailScreen(
                    navController,
                    viewModel,
                    userId,
                    isJoinView,
                    requireActivity()
                )
            }
        }

    }

    override fun onResume() {
        super.onResume()
        recommendDetailData()
    }

    fun recommendDetailData() {
        viewModel.loadFriendInfo(userId.toLong())
        viewModel.getUserOpenMeeting(userId.toLong())
        viewModel.getUserClosedMeeting(userId.toLong())
        viewModel.getReview(userId.toLong())
        viewModel.loadFriends(userId.toLong())
        mypageViewModel.friendsList.value.let { friendsList ->
            viewModel.checkIfUserIsFriend(friendsList, userId.toLong())
        }
    }

    companion object {
    }
}