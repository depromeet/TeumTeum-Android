package com.teumteum.domain.repository

interface AuthRepository {
    fun setAutoLogin(userToken: String, refreshToken: String)
    fun disableAutoLogin()
    fun getAutoLogin(): Boolean
    fun setIsFirstAfterInstall(isFirst: Boolean)
    fun getIsFirstAfterInstall(): Boolean
    fun getUserId(): Long
    fun setDeviceToken(deviceToken: String)
    fun getDeviceToken(): String
    suspend fun postDeviceToken(token: String): Boolean
    suspend fun patchDeviceToken(token: String): Boolean
    fun setAskedNotification(didAsk: Boolean)

    fun getAskedNotification(): Boolean
}