package com.teumteum.teumteum.presentation.teumteum.shake

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.base.R.color
import com.teumteum.teumteum.databinding.ActivityOnboardingBinding
import com.teumteum.teumteum.presentation.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ShakeOnBoardingActivity
    : BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding), AppBarLayout {

    private val shakeOnboardingAdapter = ShakeOnBoardingAdapter()

    private val viewpagerList = ArrayList<ShakeOnboarding>()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        startActivity(Intent(this@ShakeOnBoardingActivity, SignInActivity::class.java))
        finish()
    }


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

        setAppBarBackgroundColor(color.background)
        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_arrow_left_l,
                useRippleEffect = false,
                clickEvent = null
            )
        )
    }

    private fun initViewPagerItem() {
        with(viewpagerList) {
            add(
                ShakeOnboarding(
                    getString(R.string.shake_onboarding_location_title),
                    getString(R.string.shake_onboarding_location_subtitle),
                    ContextCompat.getDrawable(
                        this@ShakeOnBoardingActivity,
                        R.drawable.ic_shake_onboarding_location
                    )
                )
            )
            add(
                ShakeOnboarding(
                    getString(R.string.shake_onboarding_card_title),
                    getString(R.string.shake_onboarding_card_subtitle),
                    ContextCompat.getDrawable(
                        this@ShakeOnBoardingActivity,
                        R.drawable.ic_shake_onboarding_card
                    )
                )
            )
            add(
                ShakeOnboarding(
                    getString(R.string.shake_onboarding_interst_title),
                    getString(R.string.shake_onboarding_interest_subtitle),
                    ContextCompat.getDrawable(
                        this@ShakeOnBoardingActivity,
                        R.drawable.ic_shake_onboarding_interest
                    )
                )
            )
        }
    }

    private fun initViewPager() {
        shakeOnboardingAdapter.submitList(viewpagerList)
        with(binding) {
            vp.adapter = shakeOnboardingAdapter
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