package com.teumteum.teumteum.presentation.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.toast
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySignupBinding
import com.teumteum.teumteum.presentation.signup.area.PreferredAreaFragment
import com.teumteum.teumteum.presentation.signup.birthday.BirthdayFragment
import com.teumteum.teumteum.presentation.signup.character.CharacterFragment
import com.teumteum.teumteum.presentation.signup.community.CommunityFragment
import com.teumteum.teumteum.presentation.signup.complete.CardCompleteFragment
import com.teumteum.teumteum.presentation.signup.finish.SignUpFinishActivity
import com.teumteum.teumteum.presentation.signup.fix.CardFixFragment
import com.teumteum.teumteum.presentation.signup.goal.GetGoalFragment
import com.teumteum.teumteum.presentation.signup.interests.GetInterestFragment
import com.teumteum.teumteum.presentation.signup.job.CurrentJobFragment
import com.teumteum.teumteum.presentation.signup.job.ReadyJobFragment
import com.teumteum.teumteum.presentation.signup.mbti.GetMbtiFragment
import com.teumteum.teumteum.presentation.signup.name.GetNameFragment
import com.teumteum.teumteum.presentation.signup.school.CurrentSchoolFragment
import com.teumteum.teumteum.presentation.splash.MyInfoUiState
import com.teumteum.teumteum.presentation.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SignUpActivity
    : BindingActivity<ActivitySignupBinding>(R.layout.activity_signup), AppBarLayout {

    private val viewModel by viewModels<SignUpViewModel>()
    private val splashViewModel by viewModels<SplashViewModel>()
    private var oauthId = ""
    private var provider = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIdProvider()
        initAppBarLayout()
        setProgressBar()
        setStartingFragment()
        initNextButton()
        initPreviousButton()
        observer()
        userInfoObserver()
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

    private fun getIdProvider() {
        oauthId = intent.getStringExtra("oauthId").toString()
        provider = intent.getStringExtra("provider").toString()
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

    private fun hideProgressBar() {
        binding.seekBar.visibility = View.GONE
    }

    private fun changeToTwoCallButton() {
        with(binding) {
            btnNextSignup.visibility = View.GONE
            btnTwocallSignup.visibility = View.VISIBLE
        }
    }

    private fun changeToCtaButton() {
        with(binding) {
            btnTwocallSignup.visibility = View.GONE
            btnNextSignup.apply {
                visibility = View.VISIBLE
                text = getString(R.string.signup_tv_go_home)
                setOnClickListener { registerUserInfo() }
            }
        }
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

    private fun setPreviousButtonOnCardComplete() {
        binding.btnFix.setOnClickListener {
            viewModel.goToNextScreen()
            moveToCurrentProgress()
        }
        binding.btnKeep.setOnClickListener {
            registerUserInfo()
        }
        getLeftMenuChildAt(0).setOnClickListener {
            finish()
        }
    }

    private fun setPreviousButtonOnCardFix() {
        getLeftMenuChildAt(0).setOnClickListener {
            viewModel.goToPreviousScreen()
            moveToCurrentProgress()
        }
    }

    private fun goToSignUpFinishActivity() {
        val intent = Intent(this, SignUpFinishActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun observer() {
        viewModel.userInfoState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is UserInfoUiState.Success -> {
                        splashViewModel.refreshUserInfo()
                    }
                    is UserInfoUiState.Failure -> {
                        toast(it.msg)
                        finish()
                    }
                    else -> {
                        Timber.tag("teum-login").d("userInfoState: ${it.toString()}")
                    }
                }
            }
    }

    private fun userInfoObserver() {
        splashViewModel.myInfoState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is MyInfoUiState.Success -> {
                        goToSignUpFinishActivity()
                    }
                    is MyInfoUiState.Failure -> {
                        toast(it.msg)
                        finish()
                    }
                    else -> {}
                }
            }
    }

    private fun registerUserInfo() {
        viewModel.postSignUp(
            oauthId,
            provider,
            serviceAgreed = true,
            privatePolicyAgreed = true)
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
            SignUpProgress.CurrentJob -> navigateTo<CurrentJobFragment>()
            SignUpProgress.School -> navigateTo<CurrentSchoolFragment>()
            SignUpProgress.ReadyJob -> navigateTo<ReadyJobFragment>()
            SignUpProgress.Area -> navigateTo<PreferredAreaFragment>()
            SignUpProgress.Mbti -> navigateTo<GetMbtiFragment>()
            SignUpProgress.Interests -> navigateTo<GetInterestFragment>()
            SignUpProgress.Goal -> navigateTo<GetGoalFragment>()
            SignUpProgress.Complete -> completeCard()
            SignUpProgress.Fix -> fixCard()
            else -> return
        }
    }

    private fun completeCard() {
        navigateTo<CardCompleteFragment>()
        hideProgressBar()
        setPreviousButtonOnCardComplete()
        changeToTwoCallButton()
    }

    private fun fixCard() {
        navigateTo<CardFixFragment>()
        setPreviousButtonOnCardFix()
        changeToCtaButton()
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