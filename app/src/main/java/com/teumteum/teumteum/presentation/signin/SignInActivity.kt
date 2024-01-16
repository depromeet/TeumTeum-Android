package com.teumteum.teumteum.presentation.signin

import android.content.Intent
import android.os.Bundle
import androidx.compose.ui.input.key.Key.Companion.K
import com.teumteum.base.BindingActivity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySigninBinding
import com.teumteum.teumteum.presentation.signup.intro.CardIntroActivity
import com.teumteum.teumteum.presentation.signup.terms.TermsActivity
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
                startActivity(Intent(this@SignInActivity, TermsActivity::class.java))
                finish()
            }

            btnNaver.setOnClickListener {
                startActivity(Intent(this@SignInActivity, TermsActivity::class.java))
                finish()
            }
        }
    }

    companion object {
    }
}