package com.teumteum.teumteum.presentation.signin

import android.content.Intent
import android.os.Bundle
import com.teumteum.base.BindingActivity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySigninBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity
    : BindingActivity<ActivitySigninBinding>(R.layout.activity_signin){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setClickListener()
    }

    private fun setClickListener() {
        with(binding) {
            btnKakao.setOnClickListener {
                val intent = Intent(this@SignInActivity, SocialWebViewActivity::class.java)
                intent.putExtra("provider", "kakao")
                startActivity(intent)
            }

            btnNaver.setOnClickListener {
                val intent = Intent(this@SignInActivity, SocialWebViewActivity::class.java)
                intent.putExtra("provider", "naver")
                startActivity(intent)
            }
        }
    }

    companion object {
    }
}