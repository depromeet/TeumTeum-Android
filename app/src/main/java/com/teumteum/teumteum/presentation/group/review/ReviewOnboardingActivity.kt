package com.teumteum.teumteum.presentation.group.review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.longExtra
import com.teumteum.base.util.extension.stringExtra
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityReviewOnboadingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReviewOnboardingActivity: BindingActivity<ActivityReviewOnboadingBinding>(R.layout.activity_review_onboading) {
    private val meetingId by longExtra()
    private val title by stringExtra()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView() {
        title?.let {
            binding.tvTitle.text = getString(R.string.review_onboarding_title, it)
        }

        lifecycleScope.launch {
            delay(3000)
            startActivity(ReviewActivity.getIntent(this@ReviewOnboardingActivity, meetingId))
            openActivitySlideAnimation()
            finish()
        }
    }

    companion object {
        fun getIntent(context: Context, meetingId: Long, title: String) = Intent(context, ReviewOnboardingActivity::class.java).apply {
            putExtra("meetingId", meetingId)
            putExtra("title", title)
        }
    }
}