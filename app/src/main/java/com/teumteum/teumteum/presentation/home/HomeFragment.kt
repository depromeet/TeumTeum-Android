package com.teumteum.teumteum.presentation.home

import android.os.Bundle
import android.view.View
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BindingFragment<FragmentHomeBinding>(R.layout.fragment_home), AppBarLayout {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAppBarLayout()
    }

    companion object {
    }

    override val appBarBinding: LayoutCommonAppbarBinding
        get() = binding.appBar

    override fun initAppBarLayout() {
        setAppBarHeight(48)

        addMenuToLeft(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_teumteum_logo,
                useRippleEffect = false,
                clickEvent = null
            )
        )
        addMenuToRight(
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_search,
                useRippleEffect = false,
                clickEvent = null
            ),
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_alarm_active,
                useRippleEffect = false,
                clickEvent = null
            )
        )
    }
}