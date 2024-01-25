package com.teumteum.teumteum.presentation.signup.terms

import android.content.Intent
import android.os.Bundle
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.defaultToast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityTermsBinding
import com.teumteum.teumteum.presentation.signup.intro.CardIntroActivity
import com.teumteum.teumteum.util.SigninUtils.EXTRA_KEY_OAUTHID
import com.teumteum.teumteum.util.SigninUtils.EXTRA_KEY_PROVIDER
import com.teumteum.teumteum.util.SigninUtils.EXTRA_KEY_URL
import com.teumteum.teumteum.util.callback.CustomBackPressedCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsActivity
    : BindingActivity<ActivityTermsBinding>(R.layout.activity_terms) {

    private var oauthId = ""
    private var provider = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.onBackPressedDispatcher.addCallback(this,
            CustomBackPressedCallback(this, getString(R.string.alert_back_pressed_signup))
        )
        getIdProvider()
        initView()
        initTermsDetail()
    }

    private fun getIdProvider() {
        oauthId = intent.getStringExtra(EXTRA_KEY_OAUTHID).toString()
        provider = intent.getStringExtra(EXTRA_KEY_PROVIDER).toString()
    }

    private fun initView() {
        with(binding) {
            btnTermsAll.setOnClickListener {
                btnTermsAll.isSelected = !btnTermsAll.isSelected
                btnTerms1.isSelected = btnTermsAll.isSelected
                btnTerms2.isSelected = btnTermsAll.isSelected
                btnTerms3.isSelected = btnTermsAll.isSelected
                btnStart.isEnabled = btnTermsAll.isSelected
            }
            btnTerms1.setOnClickListener {
                btnTerms1.isSelected = !btnTerms1.isSelected
                btnTermsAll.isSelected = checkAllSelected()
                btnStart.isEnabled = btnTermsAll.isSelected
            }
            btnTerms2.setOnClickListener {
                btnTerms2.isSelected = !btnTerms2.isSelected
                btnTermsAll.isSelected = checkAllSelected()
                btnStart.isEnabled = btnTermsAll.isSelected
            }
            btnTerms3.setOnClickListener {
                btnTerms3.isSelected = !btnTerms3.isSelected
                btnTermsAll.isSelected = checkAllSelected()
                btnStart.isEnabled = btnTermsAll.isSelected
            }
            btnStart.setOnClickListener {
                if (btnTermsAll.isSelected) {
                    val intent = Intent(this@TermsActivity, CardIntroActivity::class.java)
                    intent.putExtra(EXTRA_KEY_OAUTHID, oauthId)
                    intent.putExtra(EXTRA_KEY_PROVIDER, provider)
                    startActivity(intent)
                    openActivitySlideAnimation()
                }
            }
        }
    }

    private fun initTermsDetail() {
        with(binding) {
            btnTermsDetail1.setOnClickListener {
                goToTermsWebView(1)
            }
            btnTermsDetail2.setOnClickListener {
                goToTermsWebView(2)
            }
            btnTermsDetail3.setOnClickListener {
                goToTermsWebView(3)
            }
        }
    }

    private fun goToTermsWebView(termsIndex: Int) {
        val intent = Intent(this@TermsActivity, TermsWebViewActivity::class.java)
        when (termsIndex) {
            1 -> intent.putExtra(EXTRA_KEY_URL, TERMS_URL_1)
            2 -> intent.putExtra(EXTRA_KEY_URL, TERMS_URL_2)
            3 -> intent.putExtra(EXTRA_KEY_URL, TERMS_URL_3)
        }
        startActivity(intent)
        openActivitySlideAnimation()
    }

    private fun checkAllSelected(): Boolean {
        with (binding) {
            return btnTerms1.isSelected && btnTerms2.isSelected && btnTerms3.isSelected
        }
    }

    override fun finish() {
        super.finish()
        closeActivitySlideAnimation()
    }

    companion object {
        const val TERMS_URL_1 = "file:///android_asset/docs_terms_service.html"
        const val TERMS_URL_2 = "file:///android_asset/docs_terms_private.html"
        const val TERMS_URL_3 = "file:///android_asset/docs_terms_location.html"
    }
}