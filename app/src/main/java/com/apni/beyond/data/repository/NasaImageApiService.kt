package com.apni.beyond.data.repository

import com.apni.beyond.data.model.NasaSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaImageApiService {

    @GET("search")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("media_type") mediaType: String = "image",
        @Query("page_size") pageSize: Int = 20
    ): NasaSearchResponse
}