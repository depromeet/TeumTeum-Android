package com.teumteum.data.repository

import com.teumteum.domain.TeumTeumDataStore
import com.teumteum.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataStore: TeumTeumDataStore,
) : UserRepository {
    override fun getUserInfo(): String {
        return dataStore.userInfo
    }

    override fun saveUserInfo(userInfo: String) {
        dataStore.userInfo = userInfo
    }
}