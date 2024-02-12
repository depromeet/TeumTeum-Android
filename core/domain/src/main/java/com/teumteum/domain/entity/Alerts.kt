package com.teumteum.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Alerts(
    val alerts: List<TeumAlert>
)
