package com.teumteum.teumteum.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.teumteum.base.BindingActivity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySplashBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.onboarding.OnBoardingActivity
import com.teumteum.teumteum.presentation.signin.SignInActivity
import com.teumteum.teumteum.util.NetworkManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity
    : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkNetwork()

    }

    private fun checkNetwork() {
        if (NetworkManager.checkNetworkState(this)) initSplash()
        else {
            AlertDialog.Builder(this)
                .setTitle("인터넷 연결")
                .setMessage("인터넷 연결을 확인해주세요.")
                .setCancelable(false)
                .setPositiveButton(
                    "확인",
                ) { _, _ ->
                    finishAffinity()
                }
                .create()
                .show()
        }
    }

    private fun initSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (viewModel.getIsFirstAfterInstall()) startOnBoarding()
            else if (!viewModel.getIsAutoLogin()) startSignIn()
            else startHomeScreen()
        }, 3000)
    }

    private fun startOnBoarding() {
        startActivity(Intent(this, OnBoardingActivity::class.java))
        finish()
    }

    private fun startSignIn() {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    private fun startHomeScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}