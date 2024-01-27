package com.teumteum.teumteum.presentation.mypage.recommend.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.compose.theme.ColorPalette_Dark
import com.teumteum.base.component.compose.theme.ColorPalette_Light
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentRecommendBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.recommend.RecommendScreen
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.RecommendDetailViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel

class RecommendFragment: BindingFragment<FragmentRecommendBinding>(R.layout.fragment_recommend) {
    private val viewModel: RecommendDetailViewModel by activityViewModels()
    private val myPageViewModel: MyPageViewModel by activityViewModels()
    private var userId: Int? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = arguments?.getInt("id") ?: null
        Log.d("my id", userId.toString())

        if(userId == 0)    myPageViewModel.loadFriends()
        else    userId?.let { viewModel.loadFriends(it.toLong()) }


        (activity as MainActivity).hideBottomNavi()
        val navController = findNavController()

        binding.composeRecommend.setContent {
            CompositionLocalProvider(TmtmColorPalette provides if(isSystemInDarkTheme()) ColorPalette_Dark else ColorPalette_Light ) {
                RecommendScreen(myPageViewModel, navController, userId, viewModel)
            }
        }

    }

    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        if(userId == 0)    myPageViewModel.loadFriends()
        else    userId?.let { viewModel.loadFriends(it.toLong()) }
        super.onResume()
    }

    companion object {
    }
}