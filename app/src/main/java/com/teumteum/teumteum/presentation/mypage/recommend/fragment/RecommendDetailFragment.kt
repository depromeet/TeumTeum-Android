package com.teumteum.teumteum.presentation.mypage.recommend.fragment

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentRecommendDetailBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.RecommendDetailScreen
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.RecommendDetailViewModel
class RecommendDetailFragment: BindingFragment<FragmentRecommendDetailBinding>(R.layout.fragment_recommend_detail) {
    private val viewModel: RecommendDetailViewModel by activityViewModels()
    private val mypageViewModel : MyPageViewModel by activityViewModels()
    private var userId: Int = -1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = arguments?.getInt("id") ?: -1
        if (userId != -1) {

            viewModel.loadFriendInfo(userId.toLong())
            viewModel.getUserOpenMeeting(userId.toLong())
            viewModel.getUserClosedMeeting(userId.toLong())
            mypageViewModel.friendsList.value.let { friendsList ->
                viewModel.checkIfUserIsFriend(friendsList, userId.toLong())
            }
        }

        (activity as MainActivity).showBottomNavi()
        val navController = findNavController()

        binding.composeRecommendDetail.setContent {
            RecommendDetailScreen(navController, viewModel, userId)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFriendInfo(userId.toLong())
        viewModel.getUserOpenMeeting(userId.toLong())
        viewModel.getUserClosedMeeting(userId.toLong())
        mypageViewModel.friendsList.value.let { friendsList ->
            viewModel.checkIfUserIsFriend(friendsList, userId.toLong())
        }
    }

    companion object {
    }
}