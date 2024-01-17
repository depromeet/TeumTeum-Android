package com.teumteum.data.service

import com.teumteum.domain.entity.SocialLoginResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {

    @GET("login/callbacks/{provider}")
    suspend fun getSocialLogin(
        @Path("provider") provider: String,
        @Query("code") code: String
    ): SocialLoginResult
}