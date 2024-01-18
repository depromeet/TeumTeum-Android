package com.teumteum.teumteum.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.teumteum.base.BindingActivity
import com.teumteum.domain.entity.JobEntity
import com.teumteum.domain.entity.UserInfo
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySplashBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.onboarding.OnBoardingActivity
import com.teumteum.teumteum.presentation.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity
    : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSplash()
    }

    private fun initSplash() {
        Handler(Looper.getMainLooper()).postDelayed({
            startOnBoarding()
        }, 3000)
    }

    private fun startOnBoarding() {
        startActivity(Intent(this, OnBoardingActivity::class.java))
        finish()
    }

    private fun saveUserInfoExample() {
        val userInfo = UserInfo(
            id = 0L,
            name = "테스트",
            birth = "2000.01.01",
            characterId = 0,
            mannerTemperature = 36,
            activityArea = "서울특별시 강남구",
            mbti = "ENFP",
            status = "학생",
            goal = "",
            job = JobEntity(
                name = "학교",
                `class` = "개발",
                detailClass = "AOS 개발자"
            ),
            interests = listOf("IT", "모여서 각자 일하기")
        )
        viewModel.saveUserInfo(userInfo)
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