package com.teumteum.data.repository

import com.teumteum.data.datasource.remote.RemoteHomeDataSource
import com.teumteum.domain.entity.RecommendMeetEntity
import com.teumteum.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val remoteHomeDataSource: RemoteHomeDataSource) :
    HomeRepository {

    //TODO : API 나오면 map { it.toEntity() } 반환 형태로 수정
    override suspend fun getRecommendMeet(): MutableList<RecommendMeetEntity> {
        return TODO("Provide the return value")
    }

}