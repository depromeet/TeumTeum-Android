package com.teumteum.teumteum.presentation.onboarding

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.teumteum.base.BindingActivity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityAccessLocationBinding
import com.teumteum.teumteum.presentation.signin.SignInActivity

class AccessLocationActivity
    : BindingActivity<ActivityAccessLocationBinding>(R.layout.activity_access_location) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        startActivity(Intent(this@AccessLocationActivity, SignInActivity::class.java))
        finish()
    }

    private fun checkLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun initView() {
        with(binding) {
            btnDecline.setOnClickListener {
                startActivity(Intent(this@AccessLocationActivity, SignInActivity::class.java))
            }
            btnAllow.setOnClickListener {
                checkLocationPermission()
            }
        }
    }

    companion object {
        const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    }
}