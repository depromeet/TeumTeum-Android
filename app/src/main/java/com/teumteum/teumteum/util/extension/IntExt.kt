package com.teumteum.teumteum.util.extension

import android.content.Context

fun Int.dpToPx(context: Context): Int {
    return context.resources.displayMetrics.density.let { density ->
        (this * density).toInt()
    }
}
