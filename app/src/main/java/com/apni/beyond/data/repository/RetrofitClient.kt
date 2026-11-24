package com.apni.beyond.data.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://science.nasa.gov/"
    private const val NASA_IMAGE_BASE_URL = "https://images-api.nasa.gov/"

    val apodApi: ApodApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApodApiService::class.java)
    }

    val nasaImageApi: NasaImageApiService by lazy {
        Retrofit.Builder()
            .baseUrl(NASA_IMAGE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaImageApiService::class.java)
    }
}