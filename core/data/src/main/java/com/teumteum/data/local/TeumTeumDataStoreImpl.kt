package com.teumteum.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.teumteum.domain.TeumTeumDataStore
import com.teumteum.domain.entity.UserInfo
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

    override var userInfo: String
        get() = userPref.getString(PREF_USER_INFO, "") ?: ""
        set(value) = userPref.edit { putString(PREF_USER_INFO, value) }

    override var deviceToken: String
        get() = userPref.getString(PREF_DEVICE_TOKEN, "") ?: ""
        set(value) = userPref.edit { putString(PREF_DEVICE_TOKEN, value) }

    override var askedNotification: Boolean
        get() = userPref.getBoolean(PREF_ASKED_NOTIFICATION, false)
        set(value) = userPref.edit { putBoolean(PREF_ASKED_NOTIFICATION, value)}

    override fun clearLocalPref() = userPref.edit { clear() }

    companion object {
        private const val PREF_USER_TOKEN = "USER_TOKEN"
        private const val PREF_REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val PREF_IS_LOGIN = "IS_LOGIN"
        private const val PREF_USER_INFO = "USER_INFO"
        private const val PREF_IS_FIRST_AFTER_INSTALL = "IS_FIRST_AFTER_INSTALL"
        private const val PREF_DEVICE_TOKEN = "DEVICE_TOKEN"
        private const val PREF_ASKED_NOTIFICATION = "ASKED_NOTIFICATION"
    }
}
