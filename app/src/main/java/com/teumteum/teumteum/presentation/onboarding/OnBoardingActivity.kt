package com.teumteum.teumteum.presentation.onboarding

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.domain.entity.CommonViewPagerEntity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityOnboardingBinding
import com.teumteum.teumteum.presentation.onboarding.adapter.OnBoardingViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity
    : BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding), AppBarLayout {

    private val onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter()

    private val viewpagerList = ArrayList<CommonViewPagerEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()
        initViewPagerItem()
        initViewPager()
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)
    }

    private fun initViewPagerItem() {
        with(viewpagerList) {
            add(CommonViewPagerEntity(getString(R.string.onboarding_tv_namecard_title), getString(R.string.onboarding_tv_namecard_subtitle), 0))
            add(CommonViewPagerEntity(getString(R.string.onboarding_tv_meet_title), getString(R.string.onboarding_tv_meet_subtitle), 0))
            add(CommonViewPagerEntity(getString(R.string.onboarding_tv_networking_title), getString(R.string.onboarding_tv_networking_subtitle), 0))
        }
    }

    private fun initViewPager() {
        onBoardingViewPagerAdapter.submitList(viewpagerList)
        with(binding) {
            vp.adapter = onBoardingViewPagerAdapter
            vp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            tl.clearOnTabSelectedListeners()
        }
        TabLayoutMediator(binding.tl, binding.vp) { tab, _ ->
            tab.view.isClickable = false
        }.attach()
    }

}