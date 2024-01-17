package com.teumteum.data.datasource.remote

import com.teumteum.data.model.request.RequestMoim
import com.teumteum.data.model.response.ResponseGroup
import com.teumteum.data.model.response.ResponseMeeting
import com.teumteum.data.service.GroupService
import com.teumteum.domain.entity.MoimRequestModel
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

    suspend fun postGroupJoin(
        meetingId: Long
    ): ResponseMeeting {
        return service.postGroupJoin(meetingId)
    }

    suspend fun deleteGroupJoin(
        meetingId: Long
    ): Boolean {
        return service.deleteGroupJoin(meetingId).isSuccessful
    }

    suspend fun postMeeting(
        requestMoim: MoimRequestModel
    ): Boolean {
        return service.postMoim(requestMoim).isSuccessful
    }
}