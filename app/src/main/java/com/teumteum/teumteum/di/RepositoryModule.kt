package com.teumteum.teumteum.di

import com.teumteum.data.repository.AuthRepositoryImpl
import com.teumteum.data.repository.GroupRepositoryImpl
import com.teumteum.data.repository.HomeRepositoryImpl
import com.teumteum.data.repository.SampleRepositoryImpl
import com.teumteum.data.repository.UserRepositoryImpl
import com.teumteum.domain.repository.AuthRepository
import com.teumteum.domain.repository.GroupRepository
import com.teumteum.domain.repository.HomeRepository
import com.teumteum.domain.repository.SampleRepository
import com.teumteum.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
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

    @Singleton
    @Binds
    fun provideGroupRepository(groupRepositoryImpl: GroupRepositoryImpl): GroupRepository

    @Singleton
    @Binds
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}