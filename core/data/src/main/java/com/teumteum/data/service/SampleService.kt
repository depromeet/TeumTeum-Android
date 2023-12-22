package com.teumteum.data.service

import com.teumteum.data.model.response.ResponseGetSample
import retrofit2.http.GET

interface SampleService {
    @GET("/endpoint")
    suspend fun getSample(
        ): ResponseGetSample

//    @POST("/endpoint")
//    suspend fun postSample(
//        @Body requestSample: RequestSample,
//    ): ResponsePostSample
//
//    @Multipart
//    @POST("/endpoint")
//    suspend fun postMultiPartSample(
//        @Part image: MultipartBody.Part,
//        @Part("data") data: RequestBody,
//    ): Response<ResponsePostMultiPartSampleDTO>
}