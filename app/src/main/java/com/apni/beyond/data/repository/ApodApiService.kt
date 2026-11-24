package com.apni.beyond.data.repository

import com.apni.beyond.data.model.ApodResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApiService {

    @GET("wp-json/wp/v2/apod-basic/")
    suspend fun getApod(
        @Query("per_page") perPage: Int = 10
    ): List<ApodResponse>

    @GET("wp-json/wp/v2/apod-basic/")
    suspend fun getRandomApod(
        @Query("count") count: Int = 25,
        @Query("_") cacheBuster: Long = System.currentTimeMillis()
    ): List<ApodResponse>

}
