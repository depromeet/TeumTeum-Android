package com.teumteum.teumteum.presentation.onboarding

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.domain.entity.CommonViewPagerEntity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityOnboardingBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.onboarding.adapter.OnBoardingViewPagerAdapter
import com.teumteum.teumteum.presentation.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity
    : BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding), AppBarLayout {

    private val onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter()

    private val viewpagerList = ArrayList<CommonViewPagerEntity>()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        startActivity(Intent(this@OnBoardingActivity, SignInActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()
        initViewPagerItem()
        initViewPager()
//        setUpListener()
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)
    }

    private fun setUpListener() {
        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun initViewPagerItem() {
        with(viewpagerList) {
            add(
                CommonViewPagerEntity(
                    getString(R.string.onboarding_tv_namecard_title),
                    getString(R.string.onboarding_tv_namecard_subtitle),
                    R.drawable.img_onboarding_1
                )
            )
            add(
                CommonViewPagerEntity(
                    getString(R.string.onboarding_tv_meet_title),
                    getString(R.string.onboarding_tv_meet_subtitle),
                    R.drawable.img_onboarding_2
                )
            )
            add(
                CommonViewPagerEntity(
                    getString(R.string.onboarding_tv_networking_title),
                    getString(R.string.onboarding_tv_networking_subtitle),
                    R.drawable.img_onboarding_3
                )
            )
        }
    }

    private fun initViewPager() {
        onBoardingViewPagerAdapter.submitList(viewpagerList)
        with(binding) {
            vp.adapter = onBoardingViewPagerAdapter
            vp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            tl.clearOnTabSelectedListeners()
            btnStart.setOnClickListener {
                checkLocationPermission()
            }
        }
        TabLayoutMediator(binding.tl, binding.vp) { tab, _ ->
            tab.view.isClickable = false
        }.attach()
    }

    private fun checkLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION
            )
        )
    }

    companion object {
        const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    }
}