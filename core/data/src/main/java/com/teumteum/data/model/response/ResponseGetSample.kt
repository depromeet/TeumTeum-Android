package com.teumteum.data.model.response

import com.teumteum.domain.entity.SampleEntity
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGetSample(
    val sample: String,
)

//MapperSample
fun ResponseGetSample.toData(): SampleEntity {
    return SampleEntity(sample = sample)
}