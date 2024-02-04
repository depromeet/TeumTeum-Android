package com.teumteum.teumteum.util

import android.content.Context
import com.google.gson.Gson
import com.teumteum.domain.entity.UserInfo

object AuthUtils {
    private const val PREF_NAME = "user_info_pref"
    private const val KEY_USER_INFO = "key_user_info"

    fun setMyInfo(context: Context, myInfo: UserInfo) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val userInfoJson = Gson().toJson(myInfo)
        editor.putString(KEY_USER_INFO, userInfoJson)
        editor.apply()
    }

    fun getMyInfo(context: Context): UserInfo? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val userInfoJson = prefs.getString(KEY_USER_INFO, null)
        return userInfoJson?.let { Gson().fromJson(it, UserInfo::class.java) }
    }

    fun removeMyInfo(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.remove(KEY_USER_INFO).apply()
    }
}