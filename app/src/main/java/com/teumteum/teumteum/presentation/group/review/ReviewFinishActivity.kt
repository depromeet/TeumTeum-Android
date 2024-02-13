package com.teumteum.teumteum.presentation.group.review

import android.content.Intent
import android.os.Bundle
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityReviewFinishBinding
import com.teumteum.teumteum.presentation.MainActivity

class ReviewFinishActivity :
    BindingActivity<ActivityReviewFinishBinding>(R.layout.activity_review_finish), AppBarLayout {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()
        initEvent()
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)

        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_arrow_left_l,
                useRippleEffect = false,
                clickEvent = {
                    goToMainActivity()
                }
            )
        )
    }

    private fun initEvent() {
        binding.btnHome.setOnSingleClickListener {
            goToMainActivity()
        }
    }

    private fun goToMainActivity() {
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(this)
        }
        openActivitySlideAnimation()
    }
}