package com.teumteum.teumteum.presentation.signup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySignupBinding
import com.teumteum.teumteum.presentation.signup.birthday.BirthdayFragment
import com.teumteum.teumteum.presentation.signup.character.CharacterFragment
import com.teumteum.teumteum.presentation.signup.community.CommunityFragment
import com.teumteum.teumteum.presentation.signup.name.GetNameFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity
    : BindingActivity<ActivitySignupBinding>(R.layout.activity_signup), AppBarLayout {

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAppBarLayout()
        setProgressBar()
        setStartingFragment()
        initNextButton()
        initPreviousButton()
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
        binding.btnNextSignup.isEnabled = true
    }

    fun disableNextButton() {
        binding.btnNextSignup.isEnabled = false
    }

    private fun initNextButton() {
        binding.btnNextSignup.setOnClickListener {
            viewModel.goToNextScreen()
            moveToCurrentProgress()

        }
    }

    private fun initPreviousButton() {
        getLeftMenuChildAt(0).setOnClickListener {
            viewModel.goToPreviousScreen()
            moveToCurrentProgress()
        }
    }

    private fun setProgressBar() {
        lifecycleScope.launch {
            viewModel.currentStep.collect { currentStep ->
                binding.seekBar.progress = currentStep * 10
                if (currentStep == 0) finish()
            }
        }
    }

    private fun moveToCurrentProgress() {
        when (viewModel.signUpProgress.value) {
            SignUpProgress.Character -> navigateTo<CharacterFragment>()
            SignUpProgress.Name -> navigateTo<GetNameFragment>()
            SignUpProgress.Birthday -> navigateTo<BirthdayFragment>()
            SignUpProgress.Community -> navigateTo<CommunityFragment>()
            else -> return
        }
    }

    inline fun <reified T : Fragment> navigateTo() {
        disableNextButton()
        supportFragmentManager.commit {
            replace<T>(R.id.fcv_signup, T::class.java.canonicalName)
        }
    }

    companion object {
    }
}