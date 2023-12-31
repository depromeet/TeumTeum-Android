package com.teumteum.teumteum.di


import com.teumteum.data.service.HomeService
import com.teumteum.data.service.SampleService
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
}