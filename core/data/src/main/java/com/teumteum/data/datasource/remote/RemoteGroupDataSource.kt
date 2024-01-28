package com.teumteum.data.datasource.remote

import com.teumteum.data.model.response.ResponseGroup
import com.teumteum.data.model.response.ResponseMeeting
import com.teumteum.data.model.response.ResponseMyMeeting
import com.teumteum.data.service.GroupService
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    suspend fun postJoinGroup(
        meetingId: Long
    ): ResponseMeeting {
        return service.postJoinGroup(meetingId)
    }

    suspend fun deleteGroupJoin(
        meetingId: Long
    ): Boolean {
        return service.deleteGroupJoin(meetingId).isSuccessful
    }

    suspend fun deleteMeeting(
        meetingId: Long
    ): Boolean {
        return service.deleteMeeting(meetingId).isSuccessful
    }

    suspend fun postMeeting(
        moimRequestBody: RequestBody,
        files: List<MultipartBody.Part>
    ): ResponseMeeting {
        return service.postMoim(moimRequestBody, files)
    }

    suspend fun getGroup(
        meetingId: Long
    ): ResponseMeeting {
        return service.getGroup(meetingId)
    }

    suspend fun modifyMeeting(
        meetingId: Long,
        moimRequestBody: RequestBody,
        files: List<MultipartBody.Part>
    ): ResponseMeeting {
        return service.modifyMeeting(meetingId, files, moimRequestBody)
    }
}