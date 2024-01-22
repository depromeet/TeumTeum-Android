package com.teumteum.data.datasource.remote

import com.teumteum.data.model.response.ResponsePostNeighborUser
import com.teumteum.data.service.NeighborService
import javax.inject.Inject

class RemoteNeighborDataSource @Inject constructor(private val neighborService: NeighborService) {
    suspend fun postNeighborUser(): ResponsePostNeighborUser =
        neighborService.getNeighboringUser()
}