package com.teumteum.teumteum.presentation.moim

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebView.setWebContentsDebuggingEnabled
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentWebviewBinding


class WebviewFragment :
    BindingFragment<FragmentWebviewBinding>(R.layout.fragment_webview){

    private val webViewModel: WebViewModel by activityViewModels()
    private lateinit var handler: Handler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWebView()
        handler = Handler()

    }

    fun initWebView() {
        with(binding.daumWebview) {
            settings.apply {
                javaScriptEnabled = true
                setRenderPriority(WebSettings.RenderPriority.HIGH)
                javaScriptCanOpenWindowsAutomatically = true
                setSupportMultipleWindows(true)
                setRenderPriority(WebSettings.RenderPriority.HIGH)
            }
            addJavascriptInterface(WebAppInterface(), "Android")
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.daumWebview.visibility =
                        View.INVISIBLE
                    super.onPageStarted(view, url, favicon)
                }
                override fun onPageFinished(view: WebView, url: String) {
                    view.loadUrl("javascript:sample2_execDaumPostcode();")
                    handler.postDelayed({
                        binding.daumWebview.visibility = View.VISIBLE
                    }, 1000)
                }
            }
            loadUrl("https://teumteum42.blogspot.com/2024/01/daum.html")
        }
    }

    private inner class WebAppInterface() {
        @JavascriptInterface
        fun processDATA(data: String) {
            handler.post {
                val fullAddress = data.toString()
                webViewModel.setAddress(fullAddress)
                Log.d("address", fullAddress.toString())
                activity?.runOnUiThread {
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    companion object {

    }
}