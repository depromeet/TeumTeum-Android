package com.teumteum.teumteum.presentation.signin

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.teumteum.base.BindingActivity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySigninBinding
import com.teumteum.teumteum.presentation.splash.SplashViewModel
import com.teumteum.teumteum.util.SigninUtils.EXTRA_KEY_PROVIDER
import com.teumteum.teumteum.util.SigninUtils.KAKAO_PROVIDER_ENG
import com.teumteum.teumteum.util.SigninUtils.NAVER_PROVIDER_ENG
import com.teumteum.teumteum.util.callback.CustomBackPressedCallback
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignInActivity
    : BindingActivity<ActivitySigninBinding>(R.layout.activity_signin){

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.onBackPressedDispatcher.addCallback(this,
            CustomBackPressedCallback(this, getString(R.string.alert_back_pressed_finish)))
        splashViewModel.setIsFirstAfterInstall(false)
        setClickListener()
    }

    private fun setClickListener() {
        with(binding) {
            btnKakao.setOnClickListener {
                val intent = Intent(this@SignInActivity, SocialWebViewActivity::class.java)
                intent.putExtra(EXTRA_KEY_PROVIDER, KAKAO_PROVIDER_ENG)
                startActivity(intent)
                openActivitySlideAnimation()
            }

            btnNaver.setOnClickListener {
                val intent = Intent(this@SignInActivity, SocialWebViewActivity::class.java)
                intent.putExtra(EXTRA_KEY_PROVIDER, NAVER_PROVIDER_ENG)
                startActivity(intent)
                openActivitySlideAnimation()
            }
        }
    }

    private fun getUserInfoExample() {
        Timber.tag("teum-datastore").d(splashViewModel.getUserInfo().toString())
    }

    override fun finish() {
        super.finish()
        closeActivitySlideAnimation()
    }

    companion object {
    }
}