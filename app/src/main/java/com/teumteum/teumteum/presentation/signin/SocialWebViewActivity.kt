package com.teumteum.teumteum.presentation.signin

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.base.util.extension.toast
import com.teumteum.data.BuildConfig
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivitySocialWebviewBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SocialWebViewActivity
    : BindingActivity<ActivitySocialWebviewBinding>(R.layout.activity_social_webview), AppBarLayout {

    private val viewModel by viewModels<SignInViewModel>()
    private var loginUrl = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val provider = intent.getStringExtra("provider")
        initProvider(provider)
        initAppBarLayout()
        initWebView(provider!!)
        observer()
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

    private fun initProvider(provider: String?) {
        when (provider) {
            KAKAO_PROVIDER -> {
                loginUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code&" +
                        "client_id=${BuildConfig.KAKAO_API_KEY}&" +
                        "redirect_uri=${BuildConfig.KAKAO_REDIRECT_URL}"
            }
            NAVER_PROVIDER -> {
                loginUrl = "https://kauth.kakao.com/oauth/authorize?response_type=code&" +
                        "client_id=${com.teumteum.data.BuildConfig.NAVER_API_KEY}&" +
                        "redirect_uri=${com.teumteum.data.BuildConfig.NAVER_REDIRECT_URL}"
            }
            else -> {
                toast("소셜 로그인에 실패했습니다")
                finish()
            }
        }
    }

    private fun initWebView(provider: String) {
        with(binding) {
            signinWebview.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    view?.loadUrl(request?.url.toString())
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    checkAuthorizationCode(url, provider)
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                }
            }
            signinWebview.loadUrl(loginUrl)
        }
    }

    private fun checkAuthorizationCode(url: String?, provider: String) {
        // 콜백 URL에서 인가코드를 감지
        if (url?.startsWith(CALLBACK_URL) == true) {
            // 인가코드를 추출하는 로직을 추가
            val uri = Uri.parse(url)
            val code = uri.getQueryParameter("code")
            if (code != null) {
                // 추출된 인가코드를 사용하여 로그인 절차를 계속 진행
                // 예: 서버에 인가코드를 보내어 액세스 토큰을 받아오는 등의 작업
                viewModel.socialLogin(provider, code)
            }
        }
    }

    private fun observer() {
        viewModel.memberState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is SignInUiState.Success -> {
                        // 바로 로그인
                        // 유저 정보 받고 받으면 (지금은 토큰만 갱신) -> goToHomeScreen
                        goToHomeScreen()

                    }
                    is SignInUiState.UserNotRegistered -> {
                        // 회원가입으로 이동
                        // oauthId 받아서 SignUpActivity로
                        val oauthId = it.oauthId
                        val intent = Intent(this, SignUpActivity::class.java)
                        intent.putExtra("oauthId", oauthId)
                        startActivity(intent)
                        finish()
                        // SignUpActivity에서 SignUpFinishActivity로 이동하기 전에 이 oauthId 포함해서 회원가입 진행
                    }
                    is SignInUiState.Failure -> {
                        toast(it.msg)
                        finish()
                    }

                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }

    private fun goToHomeScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    companion object {
        const val KAKAO_PROVIDER = "kakao"
        const val NAVER_PROVIDER = "naver"
        const val CALLBACK_URL = ""
    }

}