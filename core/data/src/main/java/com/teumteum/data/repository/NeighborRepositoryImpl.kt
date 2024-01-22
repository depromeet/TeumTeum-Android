package com.teumteum.data.repository

import com.teumteum.data.datasource.remote.RemoteNeighborDataSource
import com.teumteum.data.model.response.toEntity
import com.teumteum.domain.entity.NeighborEntity
import com.teumteum.domain.repository.NeighborRepository
import javax.inject.Inject

class NeighborRepositoryImpl @Inject constructor(private val remoteNeighborDataSource: RemoteNeighborDataSource) :
    NeighborRepository {
    override suspend fun postNeighborUser(): MutableList<NeighborEntity> {
        return remoteNeighborDataSource.postNeighborUser().aroundUserLocations.map { it.toEntity() }.toMutableList()
    }
}