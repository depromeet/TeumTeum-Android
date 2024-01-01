package com.teumteum.domain.repository

import com.teumteum.domain.entity.RecommendMeetEntity

interface HomeRepository {
    suspend fun getRecommendMeet(): MutableList<RecommendMeetEntity>
}