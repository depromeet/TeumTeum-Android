package com.teumteum.base.util.extension

import android.view.View

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible(state: Boolean) {
    if (state) visible() else gone()
}
