package com.teumteum.data.repository


import com.teumteum.data.datasource.remote.RemoteSettingDataSource
import com.teumteum.domain.TeumTeumDataStore
import com.teumteum.domain.entity.WithDrawReasons
import com.teumteum.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val dataSource: RemoteSettingDataSource
): SettingRepository {
    override suspend fun logOut(): Result<Unit> {
        return runCatching {
            dataSource.logOut()
        }
    }

    override suspend fun signOut(withDrawReasons: WithDrawReasons): Result<Unit> {
        return runCatching {
            dataSource.signOut(withDrawReasons)
        }
    }


}