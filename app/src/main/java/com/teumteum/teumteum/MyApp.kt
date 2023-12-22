package com.teumteum.teumteum

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.teumteum.teumteum.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
    }
}
