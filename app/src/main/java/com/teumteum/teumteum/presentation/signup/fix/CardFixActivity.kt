package com.teumteum.teumteum.presentation.signup.fix

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityCardFixBinding
import com.teumteum.teumteum.presentation.signup.SignUpViewModel
import com.teumteum.teumteum.presentation.signup.finish.SignUpFinishActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardFixActivity
    : BindingActivity<ActivityCardFixBinding>(R.layout.activity_card_fix), AppBarLayout {

    private val viewModel by viewModels<SignUpViewModel>()

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
        with (binding) {
            btnFinish.setOnClickListener {
                val intent = Intent(this@CardFixActivity, SignUpFinishActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
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