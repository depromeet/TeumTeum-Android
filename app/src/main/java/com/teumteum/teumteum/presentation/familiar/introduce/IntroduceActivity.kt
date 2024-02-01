package com.teumteum.teumteum.presentation.familiar.introduce

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.teumteum.base.BindingActivity
import com.teumteum.base.R.color
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.setOnSingleClickListener
import com.teumteum.domain.entity.Friend
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityIntroduceBinding
import com.teumteum.teumteum.presentation.familiar.FamiliarDialogActivity
import com.teumteum.teumteum.presentation.familiar.FamiliarDialogActivity.Companion.EXTRA_SOURCE
import com.teumteum.teumteum.presentation.familiar.FamiliarDialogActivity.Companion.SOURCE_INTRODUCE
import com.teumteum.teumteum.presentation.familiar.neighbor.NeighborActivity.Companion.EXTRA_NEIGHBORS_IDS
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable


@AndroidEntryPoint
class IntroduceActivity
    : BindingActivity<ActivityIntroduceBinding>(R.layout.activity_introduce), AppBarLayout {

    private val introduceAdapter = IntroduceAdapter()
    private val viewpagerList = ArrayList<Friend>()
    private val viewModel by viewModels<IntroduceViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntroduceUsers()
        initAppBarLayout()
        initViewPager()
        setUpListener()
        initObserver()
    }

    private fun getIntroduceUsers() {
        val ids = intent.getStringExtra(EXTRA_NEIGHBORS_IDS) ?: ""
        viewModel.getIntroduceUser(id = ids)
    }

    private fun initObserver() {
        viewModel.introduceUser.observe(this) { friends ->
            introduceAdapter.submitList(friends)
        }
    }

    private fun setUpListener() {
        binding.btnStart.setOnSingleClickListener {
            startFamiliarDialogActivity()
        }
    }

    private fun startFamiliarDialogActivity() {
        val friends = viewModel.introduceUser.value ?: listOf()
        val intent = Intent(this, FamiliarDialogActivity::class.java).apply {
            putExtra(EXTRA_FRIENDS, ArrayList(friends) as Serializable)
            putExtra(EXTRA_SOURCE, SOURCE_INTRODUCE)
        }
        startActivity(intent)
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

    companion object {
        const val EXTRA_FRIENDS = "EXTRA_FRIENDS"
    }
}