package com.teumteum.data.service

import com.teumteum.data.model.request.RequestSignOut
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SettingService {
    @POST("users/logouts")
    suspend fun logOut(): Response

    @POST("users/withdraw")
    suspend fun signOut(@Body request: RequestSignOut): Response
}