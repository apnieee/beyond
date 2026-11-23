package com.apni.beyond.data.repository

import com.apni.beyond.data.model.ApodResponse
import retrofit2.http.GET

interface ApodApiService {

    @GET("wp-json/wp/v2/apod-basic/")
    suspend fun getApod(): List<ApodResponse>
}