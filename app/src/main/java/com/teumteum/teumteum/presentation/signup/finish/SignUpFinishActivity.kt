package com.teumteum.teumteum.presentation.signup.finish

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.teumteum.base.BindingActivity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySignupFinishBinding
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFinishActivity
    : BindingActivity<ActivitySignupFinishBinding>(R.layout.activity_signup_finish) {

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        with (binding) {
            btnStart.setOnClickListener {
//                startActivity(Intent(this@SignUpFinishActivity, CardFixActivity::class.java))
            }
        }
    }

    companion object {
    }
}