package com.teumteum.data.service

import com.teumteum.data.model.response.ResponsePostNeighborUser
import retrofit2.http.Header
import retrofit2.http.POST

interface NeighborService {
    @POST("/teum-teum/arounds")
    suspend fun getNeighboringUser(
        @Header("Authorization") authorization: String = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNyIsImlhdCI6MTcwMTgxMzAyNywiZXhwIjoxNzAxODE2NjI3fQ.rFlsB9TWWhuy9f_VWobAu6OKhROb7XT7UaQN2AgD44SP2URWXsZu7ne6iE1z92fQ6v35--6FVue1tadeZF2jw"
    ): ResponsePostNeighborUser
}