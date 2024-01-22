package com.teumteum.teumteum.presentation.familiar

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "SharedPrefs"
        private const val ONBOARDING_COMPLETED = "onboarding_completed"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setOnboardingCompleted(completed: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(ONBOARDING_COMPLETED, completed)
            apply()
        }
    }

    fun isOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean(ONBOARDING_COMPLETED, false)
    }
}