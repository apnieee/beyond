package com.apni.beyond.data.repository

import com.apni.beyond.data.model.ApodResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApiService {

    @GET("planetary/apod")
    suspend fun getApod(
        @Query("api_key") apiKey: String,
        @Query("thumbs") thumbs: Boolean = true
    ): ApodResponse
}
