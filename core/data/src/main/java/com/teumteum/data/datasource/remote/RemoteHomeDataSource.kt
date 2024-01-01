package com.teumteum.data.datasource.remote

import com.teumteum.data.model.response.ResponseGetRecommendMeet
import com.teumteum.data.service.HomeService
import javax.inject.Inject

class RemoteHomeDataSource @Inject constructor(private val homeService: HomeService) {
    suspend fun getRecommendMeet(): ResponseGetRecommendMeet =
        homeService.getRecommendMeet()
}