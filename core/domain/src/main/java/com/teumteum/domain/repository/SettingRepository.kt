package com.teumteum.domain.repository

import com.teumteum.domain.entity.WithDrawReasons

interface SettingRepository {
    suspend fun logOut(): Result<Boolean>

    suspend fun signOut(withDrawReasons: WithDrawReasons): Result<Boolean>
}