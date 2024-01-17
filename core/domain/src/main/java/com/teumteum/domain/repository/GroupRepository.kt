package com.teumteum.domain.repository

import com.teumteum.domain.entity.Meeting
import com.teumteum.domain.entity.MoimEntity
import com.teumteum.domain.entity.MoimRequestModel

interface GroupRepository {

    suspend fun postGroupMoim(moimRequestModel: MoimRequestModel): Result<Boolean>
    suspend fun getSearchGroup(page: Int, keyword: String): Result<List<Meeting>>
    suspend fun postGroupJoin(meetingId: Long): Result<Meeting>
    suspend fun deleteGroupJoin(meetingId: Long): Result<Boolean>
}