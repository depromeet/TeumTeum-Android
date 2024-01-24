package com.teumteum.data.model.response


import com.teumteum.domain.entity.NeighborEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePostNeighborUser(
    @SerialName("aroundUserLocations")
    val aroundUserLocations: List<AroundUserLocation>
) {
    @Serializable
    data class AroundUserLocation(
        @SerialName("id")
        val id: Long,
        @SerialName("name")
        val name: String,
        @SerialName("jobDetailClass")
        val jobDetailClass: String,
        @SerialName("characterId")
        val characterId: Long,
    )
}

fun ResponsePostNeighborUser.AroundUserLocation.toEntity(): NeighborEntity {
    return NeighborEntity(
        id = id,
        name = name,
        jobDetailClass = jobDetailClass,
        characterId = characterId //todo - null 처리
    )
}