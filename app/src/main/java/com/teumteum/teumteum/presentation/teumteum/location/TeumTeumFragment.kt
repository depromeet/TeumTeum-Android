package com.teumteum.teumteum.presentation.teumteum.location

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.view.View
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentTeumTeumBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.teumteum.shake.onboarding.ShakeOnBoardingActivity
import com.teumteum.teumteum.util.custom.view.model.Interest
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TeumTeumFragment :
    BindingFragment<FragmentTeumTeumBinding>(R.layout.fragment_teum_teum), AppBarLayout {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAppBarLayout()
        (activity as MainActivity).hideBottomNavi()

        binding.btnStart.setOnClickListener {
            val intent = Intent(requireActivity(), ShakeOnBoardingActivity::class.java)
            startActivity(intent)
        }

        // BackCardView 인스턴스 참조
        val backCardView = binding.cvBack

        // 데이터 리스트 준비
        val interests = listOf(
            Interest(interest = "#사이드 프로젝트"),
            Interest(interest = "#네트워킹"),
            Interest(interest = "#모여서 각자 일하기"),
        )

        // 어댑터에 데이터 전달
        backCardView.submitInterestList(interests)
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)

        setAppBarBackgroundColor(com.teumteum.base.R.color.background)
        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_arrow_left_l,
                useRippleEffect = false,
                clickEvent = {}
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        // 위치 요청 interval
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        // 위치 콜백 정의
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    Timber.tag("위치").d("${location.latitude}, ${location.longitude}")
                }
            }
        }

        // 위치 업데이트 요청
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}