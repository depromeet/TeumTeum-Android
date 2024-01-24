package com.teumteum.base

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding

abstract class BindingActivity<B : ViewBinding>(@LayoutRes private val layoutResId: Int) :
    AppCompatActivity() {
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
    }

    fun openActivitySlideAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, R.anim.slide_out_left, R.anim.anim_nothing)
        } else {
            overridePendingTransition(R.anim.slide_out_left, R.anim.anim_nothing)
        }
    }

    fun closeActivitySlideAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, R.anim.anim_nothing, R.anim.slide_in_right)
        } else {
            overridePendingTransition(R.anim.anim_nothing, R.anim.slide_in_right)
        }
    }
}