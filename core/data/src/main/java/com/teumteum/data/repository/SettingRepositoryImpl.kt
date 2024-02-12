package com.teumteum.data.repository


import com.teumteum.data.datasource.remote.RemoteSettingDataSource
import com.teumteum.domain.TeumTeumDataStore
import com.teumteum.domain.entity.Meeting
import com.teumteum.domain.entity.WithDrawReasons
import com.teumteum.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val dataSource: RemoteSettingDataSource,
    private val dataStore: TeumTeumDataStore
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

    override suspend fun getMyPageOpenMeeting(participantUserId: Long): Result<List<Meeting>> {
        return runCatching {
            dataSource.getMyPageOpenMeeting(
                size = 20,
                page = 0,
                sort = "promiseDateTime",
                isOpen = true,
                participantUserId = participantUserId
            ).data.meetings.map { it.toMeeting() }
        }
    }

    override suspend fun getMyPageClosedMeeting(participantUserId: Long): Result<List<Meeting>> {
        return runCatching {
            dataSource.getMyPageClosedMeeting(
                size = 20,
                page = 0,
                sort = "promiseDateTime",
                isOpen = false,
                participantUserId = participantUserId
            ).data.meetings.map { it.toMeeting() }
        }
    }

    override fun setNotification(isActivated: Boolean) {
        dataStore.onNotification = isActivated
    }

    override fun getNotification(): Boolean = dataStore.onNotification

    override suspend fun getBookmarkMeeting(): Result<List<Meeting>> {
        return runCatching {
            dataSource.getBookmarkMeeting(
                size = 20,
                page = 0,
                sort = "promiseDateTime",
                isOpen = true,
                isBookmark = true
            ).data.meetings.map { it.toMeeting()}
        }
    }


}