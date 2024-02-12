package com.teumteum.domain.repository

import com.teumteum.domain.entity.Meeting
import com.teumteum.domain.entity.WithDrawReasons

interface SettingRepository {
    suspend fun logOut(): Result<Unit>
    suspend fun signOut(withDrawReasons: WithDrawReasons): Result<Unit>
    suspend fun getMyPageOpenMeeting(participantUserId: Long): Result<List<Meeting>>
    suspend fun getMyPageClosedMeeting(participantUserId: Long): Result<List<Meeting>>
    suspend fun getBookmarkMeeting(participantUserId: Long): Result<List<Meeting>>
}