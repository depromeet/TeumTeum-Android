package com.teumteum.data.datasource.remote

import com.teumteum.data.model.request.RequestSignOut
import com.teumteum.data.model.response.ResponseMyMeeting
import com.teumteum.data.service.SettingService
import com.teumteum.domain.entity.WithDrawReasons
import javax.inject.Inject

class RemoteSettingDataSource @Inject constructor(
    private val settingService: SettingService
) {
    suspend fun logOut(
    ): Boolean {
        return settingService.logOut().isSuccessful
    }

    suspend fun signOut(
        withDrawReasons: WithDrawReasons
    ):Boolean {
        val request = RequestSignOut(withDrawReasons.withdrawReasons)
        return settingService.signOut(request).isSuccessful
    }

    suspend fun getMyPageOpenMeeting(
        size: Int,
        page: Int,
        sort: String,
        isOpen: Boolean,
        participantUserId: Long? = null
    ): ResponseMyMeeting {
        return settingService.getMyPageOpenMeeting(size, page, sort,isOpen, participantUserId)
    }

    suspend fun getMyPageClosedMeeting(
        size: Int,
        page: Int,
        sort: String,
        isOpen: Boolean,
        participantUserId: Long? = null
    ): ResponseMyMeeting {
        return settingService.getMyPageClosedMeeting(size, page, sort,isOpen, participantUserId)
    }

    suspend fun getBookmarkMeeting(
        size: Int,
        page: Int,
        sort: String,
        isOpen: Boolean,
        participantUserId: Long? = null,
        isBookmark : Boolean
    ): ResponseMyMeeting {
        return settingService.getBookmarkMeeting(size, page, sort, isOpen, participantUserId, isBookmark)
    }

}