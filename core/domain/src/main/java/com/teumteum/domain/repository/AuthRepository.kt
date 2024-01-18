package com.teumteum.domain.repository

interface AuthRepository {
    fun setAutoLogin(userToken: String, refreshToken: String)
    fun getAutoLogin(): Boolean
    fun setIsFirstAfterInstall(isFirst: Boolean)
    fun getIsFirstAfterInstall(): Boolean
}