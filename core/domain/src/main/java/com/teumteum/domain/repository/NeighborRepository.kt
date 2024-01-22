package com.teumteum.domain.repository

import com.teumteum.domain.entity.NeighborEntity

interface NeighborRepository {
    suspend fun postNeighborUser(): MutableList<NeighborEntity>
}
