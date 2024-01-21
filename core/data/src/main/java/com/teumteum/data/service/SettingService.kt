package com.teumteum.data.service

import com.teumteum.data.model.request.RequestSignOut
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SettingService {
    @POST("users/logouts")
    suspend fun logOut(): Response<Unit>


    @POST("users/withdraw")
    suspend fun signOut(@Body request: RequestSignOut): Response<Unit>
}