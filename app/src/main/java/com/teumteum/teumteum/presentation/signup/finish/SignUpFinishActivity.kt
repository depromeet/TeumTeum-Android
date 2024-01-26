package com.teumteum.teumteum.presentation.signup.finish

import android.content.Intent
import android.os.Bundle
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySignupFinishBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.util.callback.CustomBackPressedCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFinishActivity
    : BindingActivity<ActivitySignupFinishBinding>(R.layout.activity_signup_finish) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.onBackPressedDispatcher.addCallback(this,
            CustomBackPressedCallback(this, getString(R.string.alert_back_pressed_finish))
        )
        initView()
    }

    private fun initView() {
        with (binding) {
            btnStart.setOnSingleClickListener {
                startActivity(Intent(this@SignUpFinishActivity, MainActivity::class.java))
                openActivitySlideAnimation()
                finish()
            }
        }
    }

    override fun finish() {
        super.finish()
        closeActivitySlideAnimation()
    }

    companion object {
    }
}