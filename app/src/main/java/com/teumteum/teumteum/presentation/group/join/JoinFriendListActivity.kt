package com.teumteum.teumteum.presentation.group.join

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.stringExtra
import com.teumteum.domain.entity.Friend
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityJoinFriendListBinding
import com.teumteum.teumteum.presentation.MainActivity
import kotlinx.serialization.json.Json

class JoinFriendListActivity :
    BindingActivity<ActivityJoinFriendListBinding>(R.layout.activity_join_friend_list),
    AppBarLayout {
    private lateinit var joinFriendAdapter: JoinFriendAdapter
    private val friendListJson by stringExtra()

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
                clickEvent = {
                    finish()
                    closeActivitySlideAnimation()
                }
            )
        )
    }

    private fun initView() {
        friendListJson?.let {
            val friendList = Json.decodeFromString<List<Friend>>(it)
            joinFriendAdapter = JoinFriendAdapter { friend ->
                startActivity(MainActivity.getIntent(this, friend.id))
                openActivitySlideAnimation()
            }

            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.viewPagerMargin)
            val pagerWidth = resources.getDimensionPixelOffset(R.dimen.viewPagerWidth)
            val screenWidth = resources.displayMetrics.widthPixels
            val offsetPx = screenWidth - pageMarginPx - pagerWidth

            binding.vpFriend.run {
                offscreenPageLimit = 1
                setPageTransformer { page, position ->
                    val offset = -offsetPx * position
                    page.translationX = offset
                }
                adapter = joinFriendAdapter
            }
            joinFriendAdapter.submitList(friendList)
        }

        TabLayoutMediator(binding.tl, binding.vpFriend) { tab, _ ->
            tab.view.isClickable = false
        }.attach()
    }

    companion object {
        fun getIntent(context: Context, friendListJson: String) =
            Intent(context, JoinFriendListActivity::class.java).apply {
                putExtra("friendListJson", friendListJson)
            }
    }
}