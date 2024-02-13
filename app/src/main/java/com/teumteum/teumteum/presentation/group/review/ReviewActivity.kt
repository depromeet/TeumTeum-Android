package com.teumteum.teumteum.presentation.group.review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.longExtra
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityReviewBinding
import dagger.hilt.android.AndroidEntryPoint

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
            replace(R.id.fragment_container, ReviewFriendSelectFragment())
        }
    }

    fun nextFriendDetailFragment() {
        if (viewModel.currentFriendIndex >= viewModel.selectFriendList.size) return
        with(viewModel.selectFriendList[viewModel.currentFriendIndex++]) {
            val fragment = ReviewFriendDetailFragment.newInstance(id, characterId, name, job)

            supportFragmentManager.commit {
                replace(R.id.fragment_container, fragment)
            }
        }
    }

    companion object {
        fun getIntent(context: Context, meetingId: Long) = Intent(context, ReviewActivity::class.java).apply {
            putExtra("meetingId", meetingId)
        }
    }

}