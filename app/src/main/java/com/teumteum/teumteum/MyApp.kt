package com.teumteum.teumteum

import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        setUpFlipper()
        appContext = applicationContext
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onAppBackgrounded() { isForeground = false }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAppForegrounded() { isForeground = true}

    companion object {
        lateinit var appContext: Context
        var isForeground = false
    }
}
