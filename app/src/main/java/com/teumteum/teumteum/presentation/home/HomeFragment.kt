package com.teumteum.teumteum.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.teumteum.base.BindingFragment
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentHomeBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.group.search.SearchActivity
import com.teumteum.teumteum.presentation.moim.MoimFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BindingFragment<FragmentHomeBinding>(R.layout.fragment_home), AppBarLayout {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAppBarLayout()
        binding.floatingBtn.setOnClickListener { navigateToMoimFragment() }
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
                clickEvent = {
                    Intent(requireActivity(), SearchActivity::class.java).apply {
                        startActivity(this)
                    }
                }
            ),
            AppBarMenu.IconStyle(
                resourceId = R.drawable.ic_alarm_active,
                useRippleEffect = false,
                clickEvent = null
            )
        )
    }

    private fun navigateToMoimFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_moimFragment)
    }

}