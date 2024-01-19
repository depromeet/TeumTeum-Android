package com.teumteum.domain.repository

import com.teumteum.domain.entity.Meeting
import com.teumteum.domain.entity.MoimRequestModel
import java.io.File

interface GroupRepository {
    suspend fun postGroupMoim(moimRequestModel:MoimRequestModel, imageFiles: List<File>): Result<Meeting>
    suspend fun getSearchGroup(page: Int, keyword: String): Result<List<Meeting>>
    suspend fun postGroupJoin(meetingId: Long): Result<Meeting>
    suspend fun deleteGroupJoin(meetingId: Long): Result<Boolean>
}