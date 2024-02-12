package com.teumteum.teumteum.presentation.group.review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.longExtra
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityReviewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ReviewActivity: BindingActivity<ActivityReviewBinding>(R.layout.activity_review), AppBarLayout {
    private val viewModel by viewModels<ReviewViewModel>()
    private val meetingId by longExtra()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.meetingId = meetingId
        initAppBarLayout()
        initView()
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)

        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_close,
                useRippleEffect = false,
                clickEvent = {
                    finish()
                }
            )
        )
    }

    private fun initView() {
        supportFragmentManager.commit {
            add(R.id.fragment_container,ReviewFriendSelectFragment())
        }
    }

    companion object {
        fun getIntent(context: Context, meetingId: Long) = Intent(context, ReviewActivity::class.java).apply {
            putExtra("meetingId", meetingId)
        }
    }

}