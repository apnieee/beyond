package com.apni.beyond.data.repository

import com.apni.beyond.data.model.ApodResponse

class ApodRepository {
    private val api = RetrofitClient.apodApi
    private var cachedApodList: List<ApodResponse>? = null

    suspend fun getApodList(): Result<List<ApodResponse>> {
        if (cachedApodList != null) {
            return Result.success(cachedApodList!!)
        }

        return try {
            val response = api.getApod()
            if (response.isEmpty()) {
                throw Exception("No astronomy data available.")
            }

            cachedApodList = response
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getApod(): Result<ApodResponse> {
        return getApodList().map { it.first() }
    }
}