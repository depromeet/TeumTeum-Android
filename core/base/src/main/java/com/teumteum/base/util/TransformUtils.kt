package com.teumteum.base.util

import android.content.res.Resources

object TransformUtils {

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    fun dpToPx(dp: Double): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun dpToPx(dp: Float): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
}