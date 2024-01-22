package com.teumteum.teumteum.presentation.signup

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
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
import com.teumteum.teumteum.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

    private fun hideAppbar() {
        binding.appBar.clAppBar.visibility = View.INVISIBLE
    }

    private fun showAppbar() {
        binding.appBar.clAppBar.visibility = View.VISIBLE
    }

    private fun changeToTwoCallButton() {
        with(binding) {
            btnNextSignup.visibility = View.GONE
            btnFinishSignup.visibility = View.GONE
            btnTwocallSignup.visibility = View.VISIBLE
        }
    }

    // fix 화면 나타날 때
    private fun changeToCtaButton() {
        with(binding) {
            btnTwocallSignup.visibility = View.GONE
            btnFinishSignup.visibility = View.VISIBLE
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

    // card fix 시 특정 필드를 수정하는 화면의 하단 버튼
    fun showNextButtonOnFixingField() {
        binding.appBar.clAppBar.visibility = View.INVISIBLE
        binding.btnFinishSignup.visibility = View.GONE
        binding.btnNextSignup.visibility = View.VISIBLE
    }

    fun activateFixFinishButton() {
        binding.btnFinishSignup.isEnabled = true
    }

    fun disableFixFinishButton() {
        binding.btnFinishSignup.isEnabled = false
    }

    private fun setButtonListenersOnCardComplete() {
        binding.btnFix.setOnClickListener {
            savePresentUserInfo()
            viewModel.goToNextScreen()
            moveToCurrentProgress()
        }
        binding.btnKeep.setOnClickListener {
            registerUserInfo()
        }
        binding.btnFinishSignup.setOnClickListener {
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
        binding.btnNextSignup.setOnClickListener {
            fixCard()
        }
    }

    private fun goToSignUpFinishActivity() {
        val intent = Intent(this, SignUpFinishActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.userInfoState.collect { state ->
                when (state) {
                    is UserInfoUiState.Success -> {
                        splashViewModel.refreshUserInfo()
                    }
                    is UserInfoUiState.Failure -> {
                        toast(state.msg)
                        finish()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun userInfoObserver() {
        lifecycleScope.launchWhenStarted {
            splashViewModel.myInfoState.collect { state ->
                when (state) {
                    is MyInfoUiState.Success -> {
                        goToSignUpFinishActivity()
                    }
                    is MyInfoUiState.Failure -> {
                        toast(state.msg)
                        finish()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun savePresentUserInfo() {
        viewModel.savePresentUserInfo(provider)
    }

    private fun registerUserInfo() {
        if (provider == "kakao")
            viewModel.postSignUp(
                oauthId,
                "카카오",
                serviceAgreed = true,
                privatePolicyAgreed = true)
        else if (provider == "naver")
            viewModel.postSignUp(
                oauthId,
                "네이버",
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
        hideAppbar()
        setButtonListenersOnCardComplete()
        changeToTwoCallButton()
    }

    private fun fixCard() {
        showAppbar()
        navigateTo<CardFixFragment>()
        setPreviousButtonOnCardFix()
        changeToCtaButton()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val focusView = currentFocus
        if (focusView != null) {
            val rect = Rect()
            focusView.getGlobalVisibleRect(rect)
            val x = ev!!.x.toInt()
            val y = ev.y.toInt()
            if (!rect.contains(x, y))
                hideKeyboard(focusView)

        }
        return super.dispatchTouchEvent(ev)
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