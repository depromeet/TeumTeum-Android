package com.teumteum.teumteum.presentation.familiar.neighbor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityNeighborBinding
import com.teumteum.teumteum.presentation.familiar.introduce.IntroduceActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NeighborActivity : BindingActivity<ActivityNeighborBinding>(R.layout.activity_neighbor),
    AppBarLayout {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()
        initCharacterViews()
        binding.btnStart.setOnClickListener {
            startIntroduceActivity()
        }
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

    private fun initCharacterViews() {
        with(binding) {
            setCharacterView(
                view = cvMe,
                imageRes = R.drawable.ic_bear,
                job = "AOS 개발자",
                name = "나"
            )
            setCharacterView(
                view = cv1,
                imageRes = R.drawable.ic_cat,
                job = "AOS 개발자",
                name = "신민서"
            )
            setCharacterView(
                view = cv2,
                imageRes = R.drawable.ic_rabbit,
                job = "프로덕트 디자이너",
                name = "김예은"
            )
            setCharacterView(
                view = cv3,
                imageRes = R.drawable.ic_star,
                job = "프로덕트 디자이너",
                name = "신한별"
            )
            setCharacterView(
                view = cv4,
                imageRes = R.drawable.ic_ghost,
                job = "서버 개발자",
                name = "최동근"
            )
            setCharacterView(
                view = cv5,
                imageRes = R.drawable.ic_panda,
                job = "서버 개발자",
                name = "강성민"
            )

        }
    }

    private fun setCharacterView(view: CharacterView, imageRes: Int, job: String, name: String) {
        with(view) {
            characterImage.setImageResource(imageRes)
            characterJob.text = job
            characterName.text = name
        }
    }

    private fun startIntroduceActivity() {
        startActivity(Intent(this, IntroduceActivity::class.java))
    }


    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val locationRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    Timber.tag("Location").d("${location.latitude}, ${location.longitude}")
                }
            }
        }

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