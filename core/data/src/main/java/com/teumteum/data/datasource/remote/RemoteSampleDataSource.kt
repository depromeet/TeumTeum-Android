package com.teumteum.data.datasource.remote

import com.teumteum.data.model.response.ResponseGetSample
import com.teumteum.data.service.SampleService
import javax.inject.Inject

class RemoteSampleDataSource @Inject constructor(private val sampleService: SampleService) {
    suspend fun getRecommendCourse(pageNo: String?): ResponseGetSample =
        sampleService.getSample()
}