package com.teumteum.teumteum.presentation.signup.intro

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

class CardIntroActivity
    : BindingActivity<ActivityCardIntroBinding>(R.layout.activity_card_intro), AppBarLayout {

    private var oauthId = ""
    private var provider = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                startActivity(intent)
            }
            getLeftMenuChildAt(0).setOnClickListener {
                finish()
            }
        }
    }

    companion object {
    }
}