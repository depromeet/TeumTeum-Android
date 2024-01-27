package com.teumteum.teumteum.util.callback

import android.app.Activity
import androidx.activity.OnBackPressedCallback
import com.teumteum.base.BindingActivity
import com.teumteum.base.util.extension.defaultToast

open class CustomBackPressedCallback(private val activity: Activity, private val message: String)
    : OnBackPressedCallback(true) {

    private var backKeyPressedTime = 0L

    override fun handleOnBackPressed() {
        val curTime = System.currentTimeMillis()
        val gapTime = curTime - backKeyPressedTime

        if (gapTime in 0..2000) {
            activity.finish()
        }
        else {
            backKeyPressedTime = curTime
            activity.defaultToast(message)
        }
    }
}