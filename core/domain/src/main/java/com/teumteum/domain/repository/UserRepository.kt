package com.teumteum.domain.repository

interface UserRepository {
    fun saveUserInfo(userInfo: String)
    fun getUserInfo(): String
}