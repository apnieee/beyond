package com.apni.beyond.data.repository

import com.apni.beyond.data.model.ApodResponse

class ApodRepository {

    private val api = RetrofitClient.apodApi

    suspend fun getApodList(): Result<List<ApodResponse>> {
        return try {
            val response = api.getApod()

            if (response.isEmpty()) {
                throw Exception("No astronomy data available.")
            }

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRandomApod(): Result<ApodResponse> {
        return try {
            val response = api.getRandomApod(
                count = 25,
                cacheBuster = System.currentTimeMillis()
            )

            val randomImage = response
                .filter {
                    it.mediaType.equals("image", ignoreCase = true) &&
                            !it.url.isNullOrBlank()
                }
                .randomOrNull()
                ?: throw Exception("No random astronomy image available.")

            Result.success(randomImage)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getApod(): Result<ApodResponse> {
        return getApodList().map { it.first() }
    }
}
