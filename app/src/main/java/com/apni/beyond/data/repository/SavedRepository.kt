package com.apni.beyond.data.repository

import android.content.Context

class SavedRepository(context: Context) {

    private val preferences = context.getSharedPreferences(
        "beyond_saved",
        Context.MODE_PRIVATE
    )

    fun getSavedIds(): Set<String> {
        return preferences.getStringSet("saved_ids", emptySet())
            ?.toSet() ?: emptySet()
    }

    fun isSaved(id: String): Boolean {
        return getSavedIds().contains(id)
    }

    fun toggleSaved(id: String) {
        val savedIds = getSavedIds().toMutableSet()

        if (savedIds.contains(id)) {
            savedIds.remove(id)
        } else {
            savedIds.add(id)
        }

        preferences.edit()
            .putStringSet("saved_ids", savedIds)
            .apply()
    }
}
