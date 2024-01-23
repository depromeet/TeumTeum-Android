package com.teumteum.base.util.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.teumteum.base.component.snackbar.ButtonSnackBar
import com.teumteum.base.component.snackbar.DefaultSnackBar

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.snackBar(anchorView: View, message: () -> String) {
    Snackbar.make(anchorView, message(), Snackbar.LENGTH_SHORT).show()
}

fun Context.defaultSnackBar(view: View?, message: String, anchorView: View? = null) {
    if (view != null) {
        if (anchorView == null)
            DefaultSnackBar.make(view, message).show()
        else {
            val snackbar = DefaultSnackBar.make(view, message)
            snackbar.setAnchorView(anchorView)
            snackbar.show()
        }
    }
}

fun Context.buttonSnackBar(view: View?,
                           message: String,
                           buttonText: String,
                           anchorView: View? = null,
                           onClickListener: (View?) -> Unit) {
    if (view != null) {
        if (anchorView == null)
            ButtonSnackBar.make(view, message, buttonText, onClickListener).show()
        else {
            val snackbar = ButtonSnackBar.make(view, message, buttonText, onClickListener)
            snackbar.setAnchorView(anchorView)
            snackbar.show()
        }
    }
}

fun Context.stringOf(@StringRes resId: Int) = getString(resId)

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

