package com.teumteum.teumteum.presentation.splash

import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.AuthRepository
import com.teumteum.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val gsonBuilder = GsonBuilder().create()

    fun saveUserInfo(userInfo: UserInfo) {
        Timber.tag("teum-datastore").d("saveUserInfo: $userInfo")
        repository.saveUserInfo(gsonBuilder.toJson(userInfo))
    }

    fun getUserInfo(): UserInfo {
        val userInfoGson = gsonBuilder.fromJson(repository.getUserInfo(), UserInfo::class.java)
        Timber.tag("teum-datastore").d("getUserInfo: $userInfoGson")
        return userInfoGson
    }

    fun getIsAutoLogin(): Boolean = authRepository.getAutoLogin()

    fun setIsFirstAfterInstall(isFirst: Boolean) {
        authRepository.setIsFirstAfterInstall(isFirst)
    }

    fun getIsFirstAfterInstall(): Boolean = authRepository.getIsFirstAfterInstall()
}