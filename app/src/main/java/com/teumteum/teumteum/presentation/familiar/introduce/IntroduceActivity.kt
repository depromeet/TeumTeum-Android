package com.teumteum.teumteum.presentation.familiar.introduce

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.teumteum.base.BindingActivity
import com.teumteum.base.R.color
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityIntroduceBinding
import com.teumteum.teumteum.presentation.familiar.introduce.model.Introduce
import com.teumteum.teumteum.presentation.familiar.shake.ShakeActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class IntroduceActivity
    : BindingActivity<ActivityIntroduceBinding>(R.layout.activity_introduce), AppBarLayout {

    private val introduceAdapter = IntroduceAdapter()
    private val viewpagerList = ArrayList<Introduce>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()
        initViewPagerItem()
        initViewPager()
        setUpListener()
    }

    private fun setUpListener() {
        binding.btnStart.setOnClickListener {
            startShakeActivity()
        }
    }

    private fun startShakeActivity() {
        startActivity(
            Intent(
                this@IntroduceActivity,
                ShakeActivity::class.java
            )
        )
        finish()
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)

        setAppBarBackgroundColor(color.background)
        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_arrow_left_l,
                useRippleEffect = false,
                clickEvent = ::finish
            )
        )
    }

    private fun initViewPagerItem() {
        with(viewpagerList) {
            add(
                Introduce(
                    ContextCompat.getDrawable(
                        this@IntroduceActivity,
                        R.drawable.ic_shake_onboarding_card
                    )
                )
            )
            add(
                Introduce(
                    ContextCompat.getDrawable(
                        this@IntroduceActivity,
                        R.drawable.ic_shake_onboarding_card
                    )
                )
            )
            add(
                Introduce(
                    ContextCompat.getDrawable(
                        this@IntroduceActivity,
                        R.drawable.ic_shake_onboarding_card
                    )
                )
            )
        }
    }

    private fun initViewPager() {
        introduceAdapter.submitList(viewpagerList)
        with(binding) {
            vp.adapter = introduceAdapter
            vp.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            tl.clearOnTabSelectedListeners()
        }
        TabLayoutMediator(binding.tl, binding.vp) { tab, _ ->
            tab.view.isClickable = false
        }.attach()
    }
}