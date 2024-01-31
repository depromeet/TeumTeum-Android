package com.teumteum.teumteum.presentation.familiar

import android.content.Intent
import android.os.Bundle
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentFamiliarBinding
import com.teumteum.teumteum.presentation.familiar.introduce.IntroduceActivity
import com.teumteum.teumteum.presentation.familiar.neighbor.NeighborActivity
import com.teumteum.teumteum.presentation.familiar.onboarding.FamiliarOnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FamiliarFragment :
    BindingFragment<FragmentFamiliarBinding>(R.layout.fragment_familiar) {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        if (!sharedPreferencesManager.isOnboardingCompleted()) {
            startActivity(Intent(requireContext(), FamiliarOnBoardingActivity::class.java))
        } else {
            startActivity(Intent(requireContext(), IntroduceActivity::class.java)) //todo - 테스트용 임시 랜딩
        }
    }
}