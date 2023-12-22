package com.teumteum.domain.repository

interface SampleRepository {
    suspend fun getSample(): MutableList<Any>
}