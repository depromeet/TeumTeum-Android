package com.teumteum.domain.repository

import com.teumteum.domain.entity.Meeting

interface GroupRepository {
    suspend fun getSearchGroup(page: Int, keyword: String): Result<List<Meeting>>
    suspend fun postGroupJoin(meetingId: Long): Result<Meeting>
    suspend fun deleteGroupJoin(meetingId: Long): Result<Boolean>
}