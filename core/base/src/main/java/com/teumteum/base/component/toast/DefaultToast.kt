package com.teumteum.base.component.toast

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.teumteum.base.R
import com.teumteum.base.databinding.ToastDefaultBinding

object DefaultToast {

    fun createToast(context: Context, message: String): Toast? {
        val inflater = LayoutInflater.from(context)
        val binding: ToastDefaultBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast_default, null, false)

        binding.tvSnackbarDefault.text = message

        return Toast(context).apply {
            setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM, 0, 20.toPx())
            duration = Toast.LENGTH_SHORT
            view = binding.root
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}