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
        @SerialName("characterId")
        val characterId: Int,
        @SerialName("id")
        val id: Int,
        @SerialName("jobDetailClass")
        val jobDetailClass: String,
        @SerialName("name")
        val name: String
    )
}

fun ResponsePostNeighborUser.AroundUserLocation.toEntity(): NeighborEntity {
    return NeighborEntity(
        characterId = characterId, //todo - null 처리
        id = id,
        jobDetailClass = jobDetailClass,
        name = name
    )
}