package com.teumteum.teumteum.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.defaultSnackBar
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
        if (NetworkManager.checkNetworkState(this)) checkAutoLogin()
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

    private fun checkAutoLogin() {
        if (viewModel.getIsFirstAfterInstall()) initSplash(IS_FIRST_AFTER_INSTALL)
        else if (viewModel.getIsAutoLogin()) {
            viewModel.refreshUserInfo()
            initSplash(IS_AUTO_LOGIN)
        }
        else {
            initSplash(HAVE_TO_SIGN_IN)
        }
    }

    private fun initSplash(state: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            when (state) {
                IS_FIRST_AFTER_INSTALL -> startOnBoarding()
                IS_AUTO_LOGIN -> observer()
                else -> startSignIn()
            }
        }, 3000)
    }

    private fun startOnBoarding() {
        startActivity(Intent(this, OnBoardingActivity::class.java))
        finish()
    }

    private fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.myInfoState.collect { state ->
                when (state) {
                    is MyInfoUiState.Success -> {
                        startHomeScreen()
                    }
                    is MyInfoUiState.Failure -> {
                        defaultSnackBar(binding.root, state.msg)
                        startSignIn()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun startSignIn() {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }

    private fun startHomeScreen() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    companion object {
        const val IS_FIRST_AFTER_INSTALL = 0
        const val IS_AUTO_LOGIN = 1
        const val HAVE_TO_SIGN_IN = 2
    }
}