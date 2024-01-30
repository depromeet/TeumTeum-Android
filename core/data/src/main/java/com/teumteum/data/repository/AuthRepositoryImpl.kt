package com.teumteum.data.repository

import com.google.gson.Gson
import com.teumteum.data.datasource.remote.RemoteUserDataSource
import com.teumteum.data.model.request.toDeviceToken
import com.teumteum.domain.TeumTeumDataStore
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: TeumTeumDataStore,
    private val userDataSource: RemoteUserDataSource
) : AuthRepository {

    override fun getAutoLogin(): Boolean = dataStore.isLogin

    override fun setAutoLogin(userToken: String, refreshToken: String) {
        dataStore.isLogin = true
        dataStore.userToken = userToken
        dataStore.refreshToken = refreshToken
    }

    override fun disableAutoLogin() {
        dataStore.isLogin = false
        dataStore.userToken = ""
        dataStore.refreshToken = ""
    }

    override fun getIsFirstAfterInstall(): Boolean = dataStore.isFirstAfterInstall
    override fun getUserId(): Long {
        val userInfoJson = dataStore.userInfo
        return if (userInfoJson.isNotEmpty()) {
            val userInfo = Gson().fromJson(userInfoJson, UserInfo::class.java)
            userInfo.id
        } else {
            -1
        }
    }

    override fun setIsFirstAfterInstall(isFirst: Boolean) {
        dataStore.isFirstAfterInstall = isFirst
    }

    override fun getDeviceToken(): String {
        return dataStore.deviceToken
    }

    override fun setDeviceToken(deviceToken: String) {
        dataStore.deviceToken = deviceToken
    }

    override suspend fun postDeviceToken(token: String): Boolean {
        return userDataSource.postDeviceToken(token.toDeviceToken())
    }

    override suspend fun patchDeviceToken(token: String): Boolean {
        return userDataSource.patchDeviceToken(token.toDeviceToken())
    }

    override fun setAskedNotification(didAsk: Boolean) {
        dataStore.askedNotification = didAsk
    }

    override fun getAskedNotification(): Boolean {
        return dataStore.askedNotification
    }
}