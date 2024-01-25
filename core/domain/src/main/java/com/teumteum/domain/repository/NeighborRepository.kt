package com.teumteum.domain.repository

import com.teumteum.domain.entity.NeighborEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface NeighborRepository {
    suspend fun postNeighborUser(requestPostNeighborUser: RequestPostNeighborUser): MutableList<NeighborEntity>
}

@Serializable //todo - 추후 삭제 또는 수정 예정
data class RequestPostNeighborUser(
    @SerialName("id")
    val id: Long,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("name")
    val name: String,
    @SerialName("jobDetailClass")
    val jobDetailClass: String,
    @SerialName("characterId")
    val characterId: Long,
)