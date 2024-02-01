package com.teumteum.teumteum.presentation.moim

import android.content.Intent
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
import androidx.fragment.app.viewModels
import com.teumteum.base.BindingFragment
import com.teumteum.teumteum.R
import com.teumteum.teumteum.databinding.FragmentWebviewBinding
import com.teumteum.teumteum.presentation.MainActivity
import com.teumteum.teumteum.presentation.group.join.GroupDetailActivity


class WebviewFragment :
    BindingFragment<FragmentWebviewBinding>(R.layout.fragment_webview){

    private val webViewModel: WebViewModel by activityViewModels()
    private val moimViewModel: MoimViewModel by activityViewModels()
    private lateinit var handler: Handler
    private var isGroup: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWebView()
        handler = Handler()

        Log.d("FromGroup", isGroup.toString())
        isGroup = arguments?.getBoolean("FromGroup", false) ?: false

    }

    fun initWebView() {
        with(binding.daumWebview) {
            settings.apply {
                javaScriptEnabled = true
                setRenderPriority(WebSettings.RenderPriority.HIGH)
                javaScriptCanOpenWindowsAutomatically = true
                setSupportMultipleWindows(true)
            }
            addJavascriptInterface(WebAppInterface(), "Android")
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.daumWebview.visibility = View.INVISIBLE
                    binding.progressBar.visibility= View.VISIBLE
                    super.onPageStarted(view, url, favicon)
                }
                override fun onPageFinished(view: WebView, url: String) {
                    view.loadUrl("javascript:sample2_execDaumPostcode();")
                    handler.postDelayed({
                        binding.progressBar.visibility = View.GONE
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
                moimViewModel.updateAddress(fullAddress)
                activity?.runOnUiThread {
                    if(isGroup) {
                        (activity as? MainActivity)?.returnGroupDetail(fullAddress)
                    } else {
                        parentFragmentManager.popBackStack()
                    }
                }
            }
        }
    }

    companion object {

    }
}