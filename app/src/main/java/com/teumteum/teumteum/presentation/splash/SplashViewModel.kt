package com.teumteum.teumteum.presentation.splash

import androidx.lifecycle.ViewModel
import com.teumteum.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val repository: AuthRepository
) : ViewModel() {
    fun getIsAutoLogin(): Boolean = repository.getAutoLogin()

    fun setIsFirstAfterInstall(isFirst: Boolean) {
        repository.setIsFirstAfterInstall(isFirst)
    }

    fun getIsFirstAfterInstall(): Boolean = repository.getIsFirstAfterInstall()
}