package com.apni.beyond.data.repository

import com.apni.beyond.data.model.NasaItem
import com.apni.beyond.data.model.NasaItemData

class NasaImageRepository {

    private val api = RetrofitClient.nasaImageApi

    suspend fun searchObjects(
        query: String
    ): Result<List<NasaItem>> {
        return try {
            val response = api.searchImages(
                query = query,
                pageSize = 30
            )

            val items = response.collection
                ?.items
                .orEmpty()
                .filter { item ->
                    !item.data.isNullOrEmpty() &&
                            !item.links.isNullOrEmpty()
                }

            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}