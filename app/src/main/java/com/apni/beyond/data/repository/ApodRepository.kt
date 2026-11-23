package com.apni.beyond.data.repository

import com.apni.beyond.data.model.ApodResponse

class ApodRepository {

    private val api = RetrofitClient.apodApi

    suspend fun getApod(): Result<ApodResponse> {
        return try {
            val response = api.getApod(
                apiKey = "DEMO_KEY"
            )

            Result.success(response)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
