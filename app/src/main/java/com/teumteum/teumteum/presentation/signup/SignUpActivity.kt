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
import com.teumteum.base.util.extension.defaultSnackBar
import com.teumteum.base.util.extension.setOnSingleClickListener
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
import com.teumteum.teumteum.util.SigninUtils.EXTRA_KEY_OAUTHID
import com.teumteum.teumteum.util.SigninUtils.EXTRA_KEY_PROVIDER
import com.teumteum.teumteum.util.SigninUtils.KAKAO_PROVIDER_ENG
import com.teumteum.teumteum.util.SigninUtils.KAKAO_PROVIDER_KOR
import com.teumteum.teumteum.util.SigninUtils.NAVER_PROVIDER_ENG
import com.teumteum.teumteum.util.SigninUtils.NAVER_PROVIDER_KOR
import com.teumteum.teumteum.util.callback.CustomBackPressedCallback
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
    private var isFromMainActivity = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFromCardIntro = intent.getBooleanExtra("isFromCardIntro", false)
        if (isFromCardIntro) {
            this.onBackPressedDispatcher.addCallback(this,
                CustomBackPressedCallback(this, getString(R.string.alert_back_pressed_signup))
            )
        }

        getIdProvider()
        initAppBarLayout()
        setProgressBar()
        setStartingFragment()
        setUpInitialListener()
        observer()
        userInfoObserver()

        val navigateTo = intent.getStringExtra("navigateTo")
        isFromMainActivity = intent.getBooleanExtra("isFromMainActivity", false)

        navigateTo?.let {
            navigateToFragment(it)
        }
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
        oauthId = intent.getStringExtra(EXTRA_KEY_OAUTHID).toString()
        provider = intent.getStringExtra(EXTRA_KEY_PROVIDER).toString()
    }

    private fun navigateToFragment(fragmentName: String) {
        when (fragmentName) {
            "fragment_get_interest" -> {
                val fragment = GetInterestFragment().apply {
                    arguments = Bundle().apply {
                        putBoolean("isFromSpecialPath", true)
                        val interests = intent.getStringArrayListExtra("interests")
                        putStringArrayList("selectedInterests", interests)
                        val combinedInterests = ArrayList<String>()
                        combinedInterests.addAll(viewModel.interestSelf.value)
                        combinedInterests.addAll(viewModel.interestField.value)
                        putStringArrayList("selectedInterests2", combinedInterests)
                    }
                    binding.btnNextSignup.visibility = View.GONE
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fcv_signup, fragment)
                    .commit()
            }
            else -> {
                setStartingFragment()
            }
        }
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

    private fun setUpInitialListener() {
        binding.btnNextSignup.setOnSingleClickListener {
            viewModel.goToNextScreen()
            moveToCurrentProgress()
        }
        getLeftMenuChildAt(0).setOnSingleClickListener {
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

    private fun setUpListenersOnCardComplete() {
        binding.btnFix.setOnSingleClickListener {
            savePresentUserInfo()
            viewModel.goToNextScreen()
            moveToCurrentProgress()
        }
        binding.btnKeep.setOnSingleClickListener {
            registerUserInfo()
        }
        binding.btnFinishSignup.setOnSingleClickListener {
            viewModel.goToPreviousScreen()
            moveToCurrentProgress()
            changeToTwoCallButton()
        }
        getLeftMenuChildAt(0).setOnSingleClickListener {
            finish()
        }
    }

    private fun setUpListenersOnCardFix() {
        getLeftMenuChildAt(0).setOnSingleClickListener {
            viewModel.goToPreviousScreen()
            moveToCurrentProgress()
        }
        binding.btnNextSignup.setOnSingleClickListener {
            fixCard()
        }
    }

    private fun goToSignUpFinishActivity() {
        val intent = Intent(this, SignUpFinishActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        openActivitySlideAnimation()
    }

    private fun observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.userInfoState.collect { state ->
                when (state) {
                    is UserInfoUiState.Success -> {
                        splashViewModel.refreshUserInfo()
                    }
                    is UserInfoUiState.Failure -> {
                        defaultSnackBar(binding.root, state.msg)
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
                        defaultSnackBar(binding.root, state.msg)
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
        if (provider == KAKAO_PROVIDER_ENG)
            viewModel.postSignUp(
                oauthId,
                KAKAO_PROVIDER_KOR,
                serviceAgreed = true,
                privatePolicyAgreed = true)
        else if (provider == NAVER_PROVIDER_ENG)
            viewModel.postSignUp(
                oauthId,
                NAVER_PROVIDER_KOR,
                serviceAgreed = true,
                privatePolicyAgreed = true)
    }

    private fun setProgressBar() {
        lifecycleScope.launch {
            viewModel.currentStep.collect { currentStep ->
                binding.seekBar.progress = currentStep * 10
                if (currentStep == 0) {
                    finish()
                }
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
        setUpListenersOnCardComplete()
        changeToTwoCallButton()
    }

    private fun fixCard() {
        showAppbar()
        navigateTo<CardFixFragment>()
        setUpListenersOnCardFix()
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

    override fun finish() {
        super.finish()
        closeActivitySlideAnimation()
    }

    companion object {
    }
}