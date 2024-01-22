package com.teumteum.data.model.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePostNeighborUser(
    @SerialName("characterId")
    val characterId: Int?,
    @SerialName("id")
    val id: Int,
    @SerialName("jobDetailClass")
    val jobDetailClass: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("name")
    val name: String
)