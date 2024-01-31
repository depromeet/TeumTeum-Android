package com.teumteum.teumteum.presentation.mypage

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.compose.theme.ColorPalette_Dark
import com.teumteum.base.component.compose.theme.ColorPalette_Light
import com.teumteum.base.component.compose.theme.TmtmColorPalette
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentMyPageBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.DialogEvent
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.MyPageViewModel
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingViewModel
import com.teumteum.teumteum.util.SignupUtils.CHARACTER_CARD_LIST
import com.teumteum.teumteum.util.SignupUtils.CHARACTER_CARD_LIST_BACK
import com.teumteum.teumteum.util.custom.view.model.FrontCard
import com.teumteum.teumteum.util.custom.view.model.Interest


class MyPageFragment :
    BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel: SettingViewModel by activityViewModels()
    private val myPageViewModel: MyPageViewModel by activityViewModels()

    private lateinit var frontAnimation: AnimatorSet
    private lateinit var backAnimation: AnimatorSet
    private var isFront = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        (activity as MainActivity).showBottomNavi()

        initCard()
        initCardAnim()

        binding.composeMypage.setContent {
            CompositionLocalProvider(TmtmColorPalette provides if(isSystemInDarkTheme()) ColorPalette_Dark else ColorPalette_Light ) {
                MyPageScreen(navController = navController, viewModel = viewModel, myPageViewModel = myPageViewModel)
            }
        }
    }

    private fun initCard() {
        with(myPageViewModel) {
            val fc = CHARACTER_CARD_LIST[frontCardState.value.characterResId]?.let {
               FrontCard(frontCardState.value.name, "@${frontCardState.value.company}", frontCardState.value.job,"lv.1층", "${frontCardState.value.area}에 사는", frontCardState.value.mbti, it)
            }
            if(fc != null) binding.cardviewFront.getInstance(fc)

            val interests = mutableListOf<Interest>()
            for (i in backCardState.value.interests) {
                interests.add(Interest("#$i"))
            }
            binding.cardviewBack.apply {
                tvGoalContent.text = backCardState.value.goalContent
                CHARACTER_CARD_LIST_BACK[backCardState.value.characterResId]?.let {
                    ivCharacter.setImageResource(it)
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun initCardAnim() {
        val scale = resources.displayMetrics.density
        binding.cardviewFront.cameraDistance = 8000 * scale
        binding.cardviewBack.cameraDistance = 8000 * scale

        frontAnimation = AnimatorInflater.loadAnimator(requireContext(), com.teumteum.base.R.anim.card_reverse_front) as AnimatorSet
        backAnimation = AnimatorInflater.loadAnimator(requireContext(), com.teumteum.base.R.anim.card_reverse_back) as AnimatorSet

        binding.cardviewFront.setOnSingleClickListener {
            startAnim()
        }
        binding.cardviewBack.setOnSingleClickListener {
            startAnim()
        }
    }

    private fun startAnim() {
        isFront = if (isFront) {
            frontAnimation.setTarget(binding.cardviewFront)
            backAnimation.setTarget(binding.cardviewBack)
            frontAnimation.start()
            backAnimation.start()
            false
        } else {
            frontAnimation.setTarget(binding.cardviewBack)
            backAnimation.setTarget(binding.cardviewFront)
            backAnimation.start()
            frontAnimation.start()
            true
        }
    }


    override fun onResume() {
        super.onResume()
        myPageViewModel.loadUserInfo()
        viewModel.getUserOpenMeeting()
        viewModel.getUserClosedMeeting()
    }

    companion object {
    }
}