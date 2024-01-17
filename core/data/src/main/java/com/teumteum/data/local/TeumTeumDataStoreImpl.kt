package com.teumteum.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.teumteum.domain.TeumTeumDataStore
import javax.inject.Inject

class TeumTeumDataStoreImpl @Inject constructor(
    private val userPref: SharedPreferences
) : TeumTeumDataStore {
    override var userToken: String
        get() = userPref.getString(PREF_USER_TOKEN, "") ?: ""
        set(value) = userPref.edit { putString(PREF_USER_TOKEN, value) }

    override var refreshToken: String
        get() = userPref.getString(PREF_REFRESH_TOKEN, "") ?: ""
        set(value) = userPref.edit { putString(PREF_REFRESH_TOKEN, value) }

    override var isLogin: Boolean
        get() = userPref.getBoolean(PREF_IS_LOGIN, false)
        set(value) = userPref.edit { putBoolean(PREF_IS_LOGIN, value) }

    override var isFirstAfterInstall: Boolean
        get() = userPref.getBoolean(PREF_IS_FIRST_AFTER_INSTALL, true)
        set(value) = userPref.edit { putBoolean(PREF_IS_FIRST_AFTER_INSTALL, value)}

    override fun clearLocalPref() = userPref.edit { clear() }

    companion object {
        private const val PREF_USER_TOKEN = "USER_TOKEN"
        private const val PREF_REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val PREF_IS_LOGIN = "IS_LOGIN"
        private const val PREF_IS_FIRST_AFTER_INSTALL = "IS_FIRST_AFTER_INSTALL"
    }
}
