package com.teumteum.teumteum.di

import com.teumteum.data.service.GroupService
import com.teumteum.data.service.HomeService
import com.teumteum.data.service.NeighborService
import com.teumteum.data.service.SampleService
import com.teumteum.data.service.SettingService
import com.teumteum.data.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideSampleService(teumteumRetrofit: Retrofit) =
        teumteumRetrofit.create(SampleService::class.java)

    @Singleton
    @Provides
    fun provideHomeService(teumteumRetrofit: Retrofit) =
        teumteumRetrofit.create(HomeService::class.java)

    @Singleton
    @Provides
    fun provideGroupService(retrofit: Retrofit) =
        retrofit.create(GroupService::class.java)

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit) =
        retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideNeighborService(retrofit: Retrofit) =
        retrofit.create(NeighborService::class.java)

    @Singleton
    @Provides
    fun provideSettingService(retrofit: Retrofit) =
        retrofit.create(SettingService::class.java)
}