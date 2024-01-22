package com.teumteum.teumteum.presentation.familiar.onboarding

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.teumteum.base.BindingActivity
import com.teumteum.base.R.color
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityOnboardingBinding
import com.teumteum.teumteum.presentation.familiar.SharedPreferencesManager
import com.teumteum.teumteum.presentation.familiar.neighbor.NeighborActivity
import com.teumteum.teumteum.presentation.familiar.onboarding.model.FamiliarOnBoarding
import com.teumteum.teumteum.presentation.onboarding.OnBoardingActivity
import com.teumteum.teumteum.util.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FamiliarOnBoardingActivity
    : BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding), AppBarLayout {

    private val familiarOnboardingAdapter = FamiliarOnBoardingAdapter()
    private val viewpagerList = ArrayList<FamiliarOnBoarding>()
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val locationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[OnBoardingActivity.ACCESS_FINE_LOCATION] == true && permissions[OnBoardingActivity.ACCESS_COARSE_LOCATION] == true -> {
                // 권한 허용 시
                startLocationActivity()
            }

            else -> {
                // 권한 거부 시
                showPermissionDeniedDialog()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesManager = SharedPreferencesManager(this).apply {
            this.setOnboardingCompleted(completed = true)
        }

        initAppBarLayout()
        initViewPagerItem()
        initViewPager()
        setUpListener()
    }

    private fun setUpListener() {
        binding.btnStart.setOnClickListener {
            if (PermissionUtils.isLocationPermissionGranted(this)) startLocationActivity() else showPermissionDeniedDialog()
        }
    }

    private fun startLocationActivity() {
        startActivity(
            Intent(
                this@FamiliarOnBoardingActivity,
                NeighborActivity::class.java
            )
        )
        finish()
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
                clickEvent = ::finish
            )
        )
    }

    private fun initViewPagerItem() {
        with(viewpagerList) {
            add(
                FamiliarOnBoarding(
                    getString(R.string.shake_onboarding_location_title),
                    getString(R.string.shake_onboarding_location_subtitle),
                    ContextCompat.getDrawable(
                        this@FamiliarOnBoardingActivity,
                        R.drawable.ic_shake_onboarding_location
                    )
                )
            )
            add(
                FamiliarOnBoarding(
                    getString(R.string.shake_onboarding_card_title),
                    getString(R.string.shake_onboarding_card_subtitle),
                    ContextCompat.getDrawable(
                        this@FamiliarOnBoardingActivity,
                        R.drawable.ic_shake_onboarding_card
                    )
                )
            )
            add(
                FamiliarOnBoarding(
                    getString(R.string.shake_onboarding_interst_title),
                    getString(R.string.shake_onboarding_interest_subtitle),
                    ContextCompat.getDrawable(
                        this@FamiliarOnBoardingActivity,
                        R.drawable.ic_shake_onboarding_interest
                    )
                )
            )
        }
    }

    private fun initViewPager() {
        familiarOnboardingAdapter.submitList(viewpagerList)
        with(binding) {
            vp.adapter = familiarOnboardingAdapter
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


    //TODO 임시 디자인
    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("위치 권한을 허용해주세요")
            .setMessage("지역 기반 기능 사용 시 위치 권한이 필요합니다")
            .setPositiveButton("설정") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("닫기", null)
            .show()
    }

    private fun checkLocationPermission() {
        if (!PermissionUtils.isLocationPermissionGranted(this)) {
            locationLauncher.launch(
                arrayOf(
                    ACCESS_FINE_LOCATION,
                    ACCESS_COARSE_LOCATION
                )
            )
        }
    }


    companion object {
        const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    }
}