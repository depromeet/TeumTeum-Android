package com.teumteum.teumteum.util.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
}

fun Context.getScreenWidth(): Float {
    return this@getScreenWidth.resources.displayMetrics.widthPixels.toFloat()
}

fun Context.getScreenHeight(): Float {
    return this@getScreenHeight.resources.displayMetrics.heightPixels.toFloat()
}