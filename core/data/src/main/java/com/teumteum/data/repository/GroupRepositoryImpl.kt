package com.teumteum.data.repository

import com.teumteum.data.datasource.remote.RemoteGroupDataSource
import com.teumteum.domain.entity.Meeting
import com.teumteum.domain.repository.GroupRepository
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val dataSource: RemoteGroupDataSource
) : GroupRepository {
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
}