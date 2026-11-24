package com.apni.beyond.data.repository

import android.content.Context
import com.apni.beyond.data.model.ApodResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SavedRepository(context: Context) {

    private val preferences = context.getSharedPreferences(
        "beyond_saved",
        Context.MODE_PRIVATE
    )

    private val gson = Gson()

    fun getSavedPhotos(): List<ApodResponse> {
        val json = preferences.getString("saved_photos", null)
            ?: return emptyList()

        return try {
            val type = object : TypeToken<List<ApodResponse>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getSavedIds(): Set<String> {
        return getSavedPhotos().mapNotNull { item ->
            item.postId?.toString() ?: item.date
        }.toSet()
    }

    fun isSaved(id: String): Boolean {
        return getSavedIds().contains(id)
    }

    fun toggleSaved(item: ApodResponse) {
        val photos = getSavedPhotos().toMutableList()
        val id = item.postId?.toString() ?: item.date.orEmpty()

        if (id.isEmpty()) return

        val existingIndex = photos.indexOfFirst {
            (it.postId?.toString() ?: it.date.orEmpty()) == id
        }

        if (existingIndex >= 0) {
            photos.removeAt(existingIndex)
        } else {
            photos.add(item)
        }

        preferences.edit()
            .putString("saved_photos", gson.toJson(photos))
            .apply()
    }

    fun removeSaved(id: String) {
        val photos = getSavedPhotos().filterNot {
            (it.postId?.toString() ?: it.date.orEmpty()) == id
        }

        preferences.edit()
            .putString("saved_photos", gson.toJson(photos))
            .apply()
    }
}
