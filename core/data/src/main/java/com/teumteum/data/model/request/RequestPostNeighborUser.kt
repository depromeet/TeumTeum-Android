package com.teumteum.data.model.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestPostNeighborUser(
    @SerialName("id")
    val id: Int,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("name")
    val name: String,
    @SerialName("jobDetailClass")
    val jobDetailClass: String,
    @SerialName("characterId")
    val characterId: Int?,
)