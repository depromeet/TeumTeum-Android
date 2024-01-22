package com.teumteum.teumteum.presentation.signup.terms

import android.os.Bundle
import android.webkit.CookieManager
import com.teumteum.base.BindingActivity
import com.teumteum.base.component.appbar.AppBarLayout
import com.teumteum.base.component.appbar.AppBarMenu
import com.teumteum.base.databinding.LayoutCommonAppbarBinding
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.ActivityTermsWebviewBinding

class TermsWebViewActivity
    : BindingActivity<ActivityTermsWebviewBinding>(R.layout.activity_terms_webview), AppBarLayout {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val termsUrl = intent.getStringExtra("url")
        initCookieManager()
        initAppBarLayout()
        if (termsUrl != null) {
            initWebView(termsUrl)
        }
        else finish()
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

    private fun initCookieManager() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookies(null)
    }

    private fun initWebView(url: String) {
        with(binding) {
            termsWebview.loadUrl(url)
        }
    }

    companion object {
    }
}