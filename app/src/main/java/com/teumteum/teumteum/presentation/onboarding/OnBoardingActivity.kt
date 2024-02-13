package com.teumteum.teumteum.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.domain.entity.CommonViewPagerEntity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityOnboardingBinding
import com.teumteum.teumteum.presentation.onboarding.adapter.OnBoardingViewPagerAdapter
import com.teumteum.teumteum.presentation.signin.SignInActivity
import com.teumteum.teumteum.util.PermissionUtils
import com.teumteum.teumteum.util.custom.dialog.CommonDialogConfig
import com.teumteum.teumteum.util.custom.dialog.CommonDialogFragment
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
        setUpListener()
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        startActivity(Intent(this@OnBoardingActivity, SignInActivity::class.java))
        openActivitySlideAnimation()
        finish()
    }

    private fun checkLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                PermissionUtils.ACCESS_FINE_LOCATION, PermissionUtils.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun setUpListener() {
        binding.btnStart.setOnSingleClickListener {
            initDialog()
        }
    }

    private fun initDialog() {
        CommonDialogFragment.newInstance(
            commonDialogConfig = CommonDialogConfig(
                title = getString(R.string.onboarding_tv_access_location_title),
                description = getString(R.string.onboarding_tv_access_location_subtitle),
                positiveButtonText = getString(R.string.onboarding_tv_access_location_allow),
                negativeButtonText = getString(R.string.onboarding_tv_access_location_decline)
            ),
            onPositiveButtonClicked = { checkLocationPermission() },
            onNegativeButtonClicked = { startActivity(Intent(this@OnBoardingActivity, SignInActivity::class.java))
             }
        ).show(supportFragmentManager, "CommonDialogFragmentTag")
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
        }
        TabLayoutMediator(binding.tl, binding.vp) { tab, _ ->
            tab.view.isClickable = false
        }.attach()
    }

    override fun finish() {
        super.finish()
        closeActivitySlideAnimation()
    }

    companion object {}
}