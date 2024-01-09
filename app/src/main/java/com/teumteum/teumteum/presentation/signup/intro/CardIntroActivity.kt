package com.teumteum.teumteum.presentation.signup.intro

import android.content.Intent
import android.os.Bundle
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityCardIntroBinding
import com.teumteum.teumteum.presentation.signin.SignInActivity

class CardIntroActivity
    : BindingActivity<ActivityCardIntroBinding>(R.layout.activity_card_intro), AppBarLayout {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()
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

    private fun initView() {
        startActivity(Intent(this@CardIntroActivity, SignInActivity::class.java))
    }

    companion object {
    }
}