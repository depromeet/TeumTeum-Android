package com.teumteum.data.service

import com.teumteum.data.model.response.ResponsePostNeighborUser
import retrofit2.http.Body
import retrofit2.http.POST

interface NeighborService {
    @POST("/teum-teum/arounds")
    suspend fun getNeighboringUser(
        @Body requestPostNeighborUser: com.teumteum.domain.repository.RequestPostNeighborUser
    ): ResponsePostNeighborUser
}