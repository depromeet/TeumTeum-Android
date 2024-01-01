package com.teumteum.teumteum.di

import com.teumteum.data.repository.HomeRepositoryImpl
import com.teumteum.data.repository.SampleRepositoryImpl
import com.teumteum.domain.repository.HomeRepository
import com.teumteum.domain.repository.SampleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindSampleRepository(sampleRepositoryImpl: SampleRepositoryImpl): SampleRepository

    @Singleton
    @Binds
    fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}