package com.teumteum.data.repository

import com.google.gson.Gson
import com.teumteum.domain.TeumTeumDataStore
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataStore: TeumTeumDataStore
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
}