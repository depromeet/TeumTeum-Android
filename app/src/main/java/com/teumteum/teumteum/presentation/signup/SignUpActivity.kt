package com.teumteum.teumteum.presentation.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySignupBinding
import com.teumteum.teumteum.presentation.home.HomeFragment
import com.teumteum.teumteum.presentation.signup.character.CharacterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity
    : BindingActivity<ActivitySignupBinding>(R.layout.activity_signup), AppBarLayout {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()
        setStartingFragment()
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

    private fun setStartingFragment() {
        navigateTo<CharacterFragment>()
    }

    fun activateNextButton() {
        binding.btnNext.isEnabled = true
    }

    private inline fun <reified T : Fragment> navigateTo() {
        binding.btnNext.isEnabled = false
        supportFragmentManager.commit {
            replace<T>(binding.fcv.id, T::class.java.canonicalName)
        }
    }

    companion object {
    }
}