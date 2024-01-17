package com.teumteum.data.repository

import com.teumteum.data.datasource.remote.RemoteGroupDataSource
import com.teumteum.domain.entity.Meeting
import com.teumteum.domain.entity.MoimEntity
import com.teumteum.domain.entity.MoimRequestModel
import com.teumteum.domain.repository.GroupRepository
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val dataSource: RemoteGroupDataSource
) : GroupRepository {
    override suspend fun postGroupMoim(moimRequestModel: MoimRequestModel): Result<Boolean> {
        return runCatching {
            dataSource.postMeeting(moimRequestModel)
        }
    }

    override suspend fun getSearchGroup(page: Int, keyword: String): Result<List<Meeting>> {
        return runCatching {
            dataSource.getGroups(
                size = 20,
                page = page,
                sort = "id,desc",
                isOpen = true,
                searchWord = keyword
            ).data.meetings.map { it.toMeeting() }
        }
    }

    override suspend fun postGroupJoin(meetingId: Long): Result<Meeting> {
        return runCatching {
            dataSource.postGroupJoin(meetingId).toMeeting()
        }
    }

    override suspend fun deleteGroupJoin(meetingId: Long): Result<Boolean> {
        return runCatching {
            dataSource.deleteGroupJoin(meetingId)
        }
    }
}