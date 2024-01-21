package com.teumteum.data.datasource.remote

import com.teumteum.data.model.request.RequestSignOut
import com.teumteum.data.service.SettingService
import com.teumteum.domain.entity.WithDrawReasons
import javax.inject.Inject

class RemoteSettingDataSource @Inject constructor(
    private val settingService: SettingService
) {
    suspend fun logOut(
    ): Boolean {
        return settingService.logOut().isSuccessful
    }

    suspend fun signOut(
        withDrawReasons: WithDrawReasons
    ):Boolean {
        val request = RequestSignOut(withDrawReasons.withdrawReasons)
        return settingService.signOut(request).isSuccessful
    }
}