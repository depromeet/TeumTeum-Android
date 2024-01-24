package com.teumteum.teumteum.presentation.signup.intro

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityCardIntroBinding
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import com.teumteum.teumteum.util.SigninUtils.EXTRA_KEY_OAUTHID
import com.teumteum.teumteum.util.SigninUtils.EXTRA_KEY_PROVIDER
import com.teumteum.teumteum.util.custom.view.model.Interest

class CardIntroActivity
    : BindingActivity<ActivityCardIntroBinding>(R.layout.activity_card_intro), AppBarLayout {

    private var oauthId = ""
    private var provider = ""

    private lateinit var frontAnimation: AnimatorSet
    private lateinit var backAnimation: AnimatorSet
    private var isFront = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initCardAnim()
        initAppBarLayout()
        getIdProvider()
        initView()
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)

        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_arrow_left_l,
                useRippleEffect = false,
                clickEvent = null
            )
        )
    }

    private fun getIdProvider() {
        oauthId = intent.getStringExtra(EXTRA_KEY_OAUTHID).toString()
        provider = intent.getStringExtra(EXTRA_KEY_PROVIDER).toString()
    }

    private fun initView() {
        with (binding) {
            btnStart.setOnClickListener {
                val intent = Intent(this@CardIntroActivity, SignUpActivity::class.java)
                intent.putExtra(EXTRA_KEY_OAUTHID, oauthId)
                intent.putExtra(EXTRA_KEY_PROVIDER, provider)
                intent.putExtra("isFromCardIntro", true)
                startActivity(intent)
                openActivitySlideAnimation()
            }
            getLeftMenuChildAt(0).setOnClickListener {
                finish()
            }
            val interests = listOf(
                Interest(interest = INTEREST_EXAMPLE_1),
                Interest(interest = INTEREST_EXAMPLE_2),
                Interest(interest = INTEREST_EXAMPLE_3)
            )
            binding.cardviewBack.submitInterestList(interests)
            binding.cardviewBack.isModify = false
            binding.cardviewBack.isModifyDetail = false
        }
    }

    @SuppressLint("ResourceType")
    private fun initCardAnim() {
        val scale = resources.displayMetrics.density
        binding.cardviewFront.cameraDistance = 8000 * scale
        binding.cardviewBack.cameraDistance = 8000 * scale

        frontAnimation = AnimatorInflater.loadAnimator(this, com.teumteum.base.R.anim.card_reverse_front) as AnimatorSet
        backAnimation = AnimatorInflater.loadAnimator(this, com.teumteum.base.R.anim.card_reverse_back) as AnimatorSet

        binding.cardviewFront.setOnClickListener {
            startAnim()
        }
        binding.cardviewBack.setOnClickListener {
            startAnim()
        }
    }

    private fun startAnim() {
        isFront = if (isFront) {
            frontAnimation.setTarget(binding.cardviewFront)
            backAnimation.setTarget(binding.cardviewBack)
            frontAnimation.start()
            backAnimation.start()
            false
        } else {
            frontAnimation.setTarget(binding.cardviewBack)
            backAnimation.setTarget(binding.cardviewFront)
            backAnimation.start()
            frontAnimation.start()
            true
        }
    }

    override fun finish() {
        super.finish()
        closeActivitySlideAnimation()
    }

    companion object {
        const val INTEREST_EXAMPLE_1 = "#사이드 프로젝트"
        const val INTEREST_EXAMPLE_2 = "#네트워킹"
        const val INTEREST_EXAMPLE_3 = "#모여서 각자 일하기"
    }
}