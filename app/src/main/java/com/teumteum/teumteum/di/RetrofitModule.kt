package com.teumteum.teumteum.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TeumTeum
    
    @Provides
    @Singleton
    fun provideOkHttpClient(
        logger: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder().addInterceptor(logger)
        .build()

    @Provides
    @Singleton
    fun provideLogger(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @OptIn(ExperimentalSerializationApi::class, InternalCoroutinesApi::class)
    @Provides
    @Singleton
    @TeumTeum
    fun provideTeumTeumRetrofit(json: Json, client: OkHttpClient): Retrofit {
        kotlinx.coroutines.internal.synchronized(this) {
            val retrofit = Retrofit.Builder().baseUrl("").client(client)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
            return retrofit ?: throw RuntimeException("Retrofit creation failed.")
        }
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }
}