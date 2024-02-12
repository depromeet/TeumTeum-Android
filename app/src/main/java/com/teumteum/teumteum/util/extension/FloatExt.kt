package com.teumteum.teumteum.util.extension

import android.content.Context

fun Float.dpToPx(context: Context): Int {
    return context.resources.displayMetrics.density.let { density ->
        (this * density).toInt()
    }
}
