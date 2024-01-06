package com.teumteum.teumteum.presentation.onboarding

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.teumteum.base.BindingActivity
import com.teumteum.domain.entity.ViewPagerEntity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityOnboardingBinding
import com.teumteum.teumteum.presentation.onboarding.adapter.OnBoardingViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity
    : BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {

    private val onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter()

    private val viewpagerList = ArrayList<ViewPagerEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewPagerItem()
        initViewPager()
    }

    private fun initViewPagerItem() {
        viewpagerList.add(ViewPagerEntity(getString(R.string.onboarding_tv_namecard_title), getString(R.string.onboarding_tv_namecard_subtitle), 0))
        viewpagerList.add(ViewPagerEntity(getString(R.string.onboarding_tv_meet_title), getString(R.string.onboarding_tv_meet_subtitle), 0))
        viewpagerList.add(ViewPagerEntity(getString(R.string.onboarding_tv_networking_title), getString(R.string.onboarding_tv_networking_subtitle), 0))
    }

    private fun initViewPager() {
        onBoardingViewPagerAdapter.submitList(viewpagerList)
        binding.vp.adapter = onBoardingViewPagerAdapter
        binding.vp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.tl.clearOnTabSelectedListeners()
        TabLayoutMediator(binding.tl, binding.vp) { tab, _ ->
            tab.view.isClickable = false
        }.attach()
    }

}