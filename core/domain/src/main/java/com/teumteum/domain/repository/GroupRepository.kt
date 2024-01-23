package com.teumteum.domain.repository

import com.teumteum.domain.entity.Meeting
import com.teumteum.domain.entity.MoimEntity
import java.io.File

interface GroupRepository {
    suspend fun postGroupMoim(moimEntity:MoimEntity, imageFiles: List<File>): Result<Meeting>
    suspend fun getSearchGroup(page: Int, keyword: String? = null, location: String? = null, topic: String? = null): Result<Pair<Boolean, List<Meeting>>>
    suspend fun postJoinGroup(meetingId: Long): Result<Meeting>
    suspend fun getGroup(meetingId: Long): Result<Meeting>
    suspend fun deleteGroupJoin(meetingId: Long): Result<Boolean>
}