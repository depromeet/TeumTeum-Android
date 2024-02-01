package com.teumteum.teumteum.presentation.familiar

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.teumteum.base.BindingActivity
import com.teumteum.domain.entity.Friend
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityFamiliarDialogBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.familiar.introduce.IntroduceActivity.Companion.EXTRA_FRIENDS
import com.teumteum.teumteum.presentation.shaketopic.ShakeTopicActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class FamiliarDialogActivity :
    BindingActivity<ActivityFamiliarDialogBinding>(R.layout.activity_familiar_dialog) {

    private lateinit var friends: List<Friend>
    private var source: String = ""

    companion object {
        const val EXTRA_SOURCE = "EXTRA_SOURCE"
        const val SOURCE_INTRODUCE = "SOURCE_INTRODUCE"
        const val SOURCE_TOPIC = "SOURCE_TOPIC"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getData()
        setUpUI()
        setUpListener()
    }

    private fun getData() {
        friends = intent.getSerializableExtra(EXTRA_FRIENDS) as? List<Friend> ?: listOf()
        source = intent.getStringExtra(EXTRA_SOURCE) ?: ""
    }

    private fun setUpUI() {
        when (source) {
            SOURCE_TOPIC -> {
                configureUI(
                    View.GONE,
                    getString(R.string.familiar_dialog_title_exit),
                    getString(R.string.familiar_dialog_subtitle_exit),
                    getString(R.string.exit)
                )
            }
            SOURCE_INTRODUCE -> {
                configureUI(
                    View.VISIBLE,
                    getString(R.string.familiar_dialog_title_shake),
                    getString(R.string.shake_onboarding_interest_subtitle),
                    getString(R.string.shake)
                )
            }
        }
    }

    private fun configureUI(ivInterestVisibility: Int, titleText: String, subtitleText: String, buttonText: String) {
        binding.ivInterest.visibility = ivInterestVisibility
        binding.tvTitle.text = titleText
        binding.tvSubtitle.text = subtitleText
        binding.btnAllow.text = buttonText
    }

    private fun setUpListener() {
        binding.btnAllow.setOnClickListener {
            startActivity()
        }
    }

    private fun startActivity() {
        when (source) {
            SOURCE_INTRODUCE -> {
                val intent = Intent(this, ShakeTopicActivity::class.java).apply {
                    putExtra(EXTRA_FRIENDS, friends as Serializable)
                }
                startActivity(intent)
            }
            SOURCE_TOPIC -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}

