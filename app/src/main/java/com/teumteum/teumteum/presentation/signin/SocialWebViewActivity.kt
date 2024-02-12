package com.teumteum.teumteum.presentation.signin

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.gson.GsonBuilder
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.defaultSnackBar
import com.teumteum.base.util.extension.defaultToast
import com.teumteum.data.BuildConfig
import com.teumteum.domain.entity.SocialLoginResult
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySocialWebviewBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.signup.terms.TermsActivity
import com.teumteum.teumteum.presentation.splash.MyInfoUiState
import com.teumteum.teumteum.presentation.splash.SplashViewModel
import com.teumteum.teumteum.util.SigninUtils.EXTRA_KEY_OAUTHID
import com.teumteum.teumteum.util.SigninUtils.EXTRA_KEY_PROVIDER
import com.teumteum.teumteum.util.SigninUtils.KAKAO_PROVIDER_ENG
import com.teumteum.teumteum.util.SigninUtils.NAVER_PROVIDER_ENG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class SocialWebViewActivity
    : BindingActivity<ActivitySocialWebviewBinding>(R.layout.activity_social_webview), AppBarLayout {

    private val viewModel by viewModels<SignInViewModel>()
    private val splashViewModel by viewModels<SplashViewModel>()
    private var loginUrl = ""
    private var provider = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        provider = intent.getStringExtra(EXTRA_KEY_PROVIDER).toString()
        initProvider(provider)
//        initCookieManager()
        initAppBarLayout()
        initWebView()
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
                clickEvent = ::finish
            )
        )
    }

    private fun initProvider(provider: String) {
        Timber.tag("teum-login").d("provider: $provider")
        when (provider) {
            KAKAO_PROVIDER_ENG -> {
                loginUrl = KAKAO_BASE_URL +
                        "&client_id=${BuildConfig.KAKAO_API_KEY}" +
                        "&redirect_uri=${BuildConfig.KAKAO_REDIRECT_URL}"
            }
            NAVER_PROVIDER_ENG -> {
                loginUrl = NAVER_BASE_URL +
                        "&client_id=${BuildConfig.NAVER_API_KEY}" +
                        "&redirect_uri=${BuildConfig.NAVER_REDIRECT_URL}"
            }
            else -> {
                defaultSnackBar(binding.root, getString(R.string.signin_tv_fail_snackbar))
                finish()
            }
        }
    }

    private fun initCookieManager() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookies(null)
    }


    private fun initWebView() {
        with(binding) {
            signinWebview.settings.javaScriptEnabled = true
            signinWebview.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    view?.loadUrl(request?.url.toString())
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    val isKakaoRedirect = url?.startsWith(BuildConfig.KAKAO_REDIRECT_URL) == true
                    val isNaverRedirect = url?.startsWith(BuildConfig.NAVER_REDIRECT_URL) == true
                    if (isKakaoRedirect || isNaverRedirect)
                        signinWebview.visibility = View.INVISIBLE
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    val isKakaoRedirect = url?.startsWith(BuildConfig.KAKAO_REDIRECT_URL) == true
                    val isNaverRedirect = url?.startsWith(BuildConfig.NAVER_REDIRECT_URL) == true
                    if (isKakaoRedirect || isNaverRedirect)
                        getJsonFromJavaScript(view)
                    super.onPageFinished(view, url)
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    Timber.tag("teum-login").d("error: ${error.toString()}")
                    super.onReceivedError(view, request, error)
                }
            }
            signinWebview.loadUrl(loginUrl)
        }
    }

    private fun getJsonFromJavaScript(view: WebView?) {
        // JavaScript를 실행하여 바디 태그 안의 JSON을 가져오기
        view?.evaluateJavascript(WEBVIEW_SCRIPT) { jsonString ->
            // jsonString에는 가져온 JSON 정보가 포함됩니다.
            // 이후에는 jsonString을 파싱하여 필요한 작업을 수행합니다.
            handleJsonString(jsonString)
        }
    }

    private fun handleJsonString(jsonString: String) {
        try {
            val gsonBuilder = GsonBuilder().create()
            val socialLoginResult =
                gsonBuilder
                    .fromJson(jsonString.drop(1).dropLast(1).replace("\\", ""),
                    SocialLoginResult::class.java)
            viewModel.updateMemberState(socialLoginResult)
        } catch (e: Exception) {
            // JSON 파싱 중 에러 발생 시 처리
            e.printStackTrace()
        }
    }

    private fun observer() {
        viewModel.memberState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is SignInUiState.Success -> {
                        // 바로 로그인
                        // 유저 정보 받고 받으면 -> goToHomeScreen
                        splashViewModel.refreshUserInfo()
                    }
                    is SignInUiState.UserNotRegistered -> {
                        goToTermsActivity()
                    }
                    is SignInUiState.Failure -> {
                        defaultToast(it.msg)
                        finish()
                    }

                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }

    private fun userInfoObserver() {
        lifecycleScope.launchWhenStarted {
            splashViewModel.myInfoState.collect { state ->
                when (state) {
                    is MyInfoUiState.Success -> {
                        goToHomeScreen()
                    }
                    is MyInfoUiState.Failure -> {
                        defaultToast(state.msg)
                        goToTermsActivity()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun goToTermsActivity() {
        // 회원가입으로 이동
        // oauthId 받아서 SignUpActivity로
        val intent = Intent(this, TermsActivity::class.java)
        intent.putExtra(EXTRA_KEY_OAUTHID, viewModel.oauthId)
        intent.putExtra(EXTRA_KEY_PROVIDER, provider)
        startActivity(intent)
        openActivitySlideAnimation()
        finish()
        // SignUpActivity에서 SignUpFinishActivity로 이동하기 전에 이 oauthId 포함해서 회원가입 진행
    }

    private fun goToHomeScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        openActivitySlideAnimation()
    }

    override fun finish() {
        super.finish()
        closeActivitySlideAnimation()
    }

    companion object {
        const val KAKAO_BASE_URL = "https://kauth.kakao.com/oauth/authorize?response_type=code&state=STATE_STRING"
        const val NAVER_BASE_URL = "https://nid.naver.com/oauth2.0/authorize?response_type=code&state=STATE_STRING"

        const val WEBVIEW_SCRIPT = "(function() { " +
                "   var body = document.getElementsByTagName('body')[0]; " +
                "   if (body) { " +
                "       var jsonElement = body.querySelector('pre'); " +
                "       if (jsonElement) { " +
                "           return jsonElement.textContent; " +
                "       } " +
                "   } " +
                "   return ''; " +
                "})();"
    }

}