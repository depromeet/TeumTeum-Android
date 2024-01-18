package com.teumteum.teumteum.presentation.splash

import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val repository: UserRepository
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
}