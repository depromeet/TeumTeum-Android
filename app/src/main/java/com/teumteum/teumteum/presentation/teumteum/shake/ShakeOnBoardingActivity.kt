package com.teumteum.teumteum.presentation.teumteum.shake

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
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
import com.teumteum.teumteum.presentation.onboarding.OnBoardingActivity
import com.teumteum.teumteum.presentation.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class ShakeOnBoardingActivity
    : BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding), AppBarLayout {

    private val shakeOnboardingAdapter = ShakeOnBoardingAdapter()

    private val viewpagerList = ArrayList<ShakeOnboarding>()

    private val locationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[OnBoardingActivity.ACCESS_FINE_LOCATION] == true && permissions[OnBoardingActivity.ACCESS_COARSE_LOCATION] == true -> {
                // 권한 허용 시
            }

            else -> {
                // 권한 거부 시
                showPermissionDeniedDialog()
            }
        }
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
                clickEvent = ::finish
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

    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            OnBoardingActivity.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    OnBoardingActivity.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
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
        if (!isLocationPermissionGranted()) {
            locationLauncher.launch(arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION
            ))
        }
    }


    companion object {
        const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    }
}