package com.teumteum.teumteum.presentation.signin

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.teumteum.base.BindingActivity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySigninBinding
import com.teumteum.teumteum.presentation.signup.terms.TermsActivity
import com.teumteum.teumteum.presentation.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SignInActivity
    : BindingActivity<ActivitySigninBinding>(R.layout.activity_signin){

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setClickListener()
    }

    private fun setClickListener() {
        with(binding) {
            btnKakao.setOnClickListener {
                startActivity(Intent(this@SignInActivity, TermsActivity::class.java))
                finish()
            }

            btnNaver.setOnClickListener {
                startActivity(Intent(this@SignInActivity, TermsActivity::class.java))
                finish()
            }
        }
    }

    private fun getUserInfoExample() {
        Timber.tag("teum-datastore").d(splashViewModel.getUserInfo().toString())
    }

    companion object {
    }
}