package com.apni.beyond.data.repository

import com.apni.beyond.data.model.ApodResponse

class ApodRepository {

    private val api = RetrofitClient.apodApi
    private var cachedApod: ApodResponse? = null

    suspend fun getApod(): Result<ApodResponse> {
        if (cachedApod != null) {
            return Result.success(cachedApod!!)
        }

        return try {
            val response = api.getApod()
            val todayApod = response.firstOrNull()
                ?: throw Exception("No astronomy data available.")

            cachedApod = todayApod
            Result.success(todayApod)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}