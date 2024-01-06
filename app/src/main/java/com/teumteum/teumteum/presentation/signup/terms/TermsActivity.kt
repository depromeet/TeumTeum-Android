package com.teumteum.teumteum.presentation.signup.terms

import android.os.Bundle
import com.teumteum.base.BindingActivity
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityTermsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsActivity
    : BindingActivity<ActivityTermsBinding>(R.layout.activity_terms) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
    }
}