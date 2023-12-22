package com.teumteum.data.repository

import com.teumteum.data.datasource.remote.RemoteSampleDataSource
import com.teumteum.domain.repository.SampleRepository
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(private val remoteSampleDataSource: RemoteSampleDataSource) :
    SampleRepository {
    override suspend fun getSample(): MutableList<Any> {
        TODO("Not yet implemented")
    }

}