package com.teumteum.teumteum.presentation.signup.terms

import android.content.Intent
import android.os.Bundle
import com.teumteum.base.BindingActivity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityTermsBinding
import com.teumteum.teumteum.presentation.signup.intro.CardIntroActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsActivity
    : BindingActivity<ActivityTermsBinding>(R.layout.activity_terms) {

    private var oauthId = ""
    private var provider = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIdProvider()
        initView()
    }

    private fun getIdProvider() {
        oauthId = intent.getStringExtra("oauthId").toString()
        provider = intent.getStringExtra("provider").toString()
    }

    private fun initView() {
        with(binding) {
            btnTermsAll.setOnClickListener {
                btnTermsAll.isSelected = !btnTermsAll.isSelected
                btnTerms1.isSelected = btnTermsAll.isSelected
                btnTerms2.isSelected = btnTermsAll.isSelected
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
            btnStart.setOnClickListener {
                if (btnTermsAll.isSelected) {
                    val intent = Intent(this@TermsActivity, CardIntroActivity::class.java)
                    intent.putExtra("oauthId", oauthId)
                    intent.putExtra("provider", provider)
                    startActivity(intent)
                }
            }
        }
    }

    private fun checkAllSelected(): Boolean {
        with (binding) {
            return btnTerms1.isSelected && btnTerms2.isSelected
        }
    }

    companion object {
    }
}