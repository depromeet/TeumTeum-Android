package com.teumteum.data.repository

import android.util.Log
import com.google.gson.Gson
import com.teumteum.data.datasource.remote.RemoteGroupDataSource
import com.teumteum.data.model.request.toBody
import com.teumteum.domain.entity.Meeting
import com.teumteum.domain.entity.MoimEntity
import com.teumteum.domain.repository.GroupRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val dataSource: RemoteGroupDataSource
) : GroupRepository {
    override suspend fun postGroupMoim(moimEntity: MoimEntity, imageFiles: List<File>): Result<Meeting> {
        return runCatching {
            Log.d("postGroupMoim", "돌아감")
            val gson = Gson()
            val moimJson = gson.toJson(moimEntity.toBody())
            val moimRequestBody = moimJson.toRequestBody("application/json".toMediaTypeOrNull())

            val imageParts = imageFiles.map { file ->
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("images", file.name, requestFile)
            }

            dataSource.postMeeting(moimRequestBody, imageParts).toMeeting()
        }
    }

    override suspend fun getSearchGroup(page: Int, keyword: String?, location: String?, topic: String?): Result<Pair<Boolean,List<Meeting>>> {
        val groupData = dataSource.getGroups(
        size = 20,
        page = page,
        sort = "promiseDateTime,desc",
        isOpen = true,
        searchWord = keyword,
        meetingAreaStreet = location,
        topic = topic
        )
        return runCatching {
            Pair(groupData.hasNext, groupData.data.meetings.map { it.toMeeting() })
        }
    }

    override suspend fun postJoinGroup(meetingId: Long): Result<Meeting> {
        return runCatching {
            dataSource.postJoinGroup(meetingId).toMeeting()
        }
    }

    override suspend fun getGroup(meetingId: Long): Result<Meeting> {
        return runCatching {
            dataSource.getGroup(meetingId).toMeeting()
        }
    }

    override suspend fun deleteGroupJoin(meetingId: Long): Result<Boolean> {
        return runCatching {
            dataSource.deleteGroupJoin(meetingId)
        }
    }
}