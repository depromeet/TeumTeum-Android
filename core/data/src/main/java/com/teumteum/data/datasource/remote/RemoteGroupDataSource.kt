package com.teumteum.data.datasource.remote

import com.teumteum.data.model.response.ResponseGroup
import com.teumteum.data.service.GroupService
import javax.inject.Inject

class RemoteGroupDataSource @Inject constructor(
    private val service: GroupService
) {
    suspend fun getGroups(
        size: Int,
        page: Int,
        sort: String,
        isOpen: Boolean,
        topic: String? = null,
        meetingAreaStreet: String? = null,
        participantUserId: Long? = null,
        searchWord: String? = null
    ): ResponseGroup {
        return service.getGroups(size, page, sort, isOpen, topic, meetingAreaStreet, participantUserId, searchWord)
    }
}