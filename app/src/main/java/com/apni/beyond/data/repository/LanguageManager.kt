package com.apni.beyond.data.repository

import android.content.Context

class LanguageManager(context: Context) {

    private val preferences = context.getSharedPreferences(
        "beyond_settings",
        Context.MODE_PRIVATE
    )

    fun getLanguage(): String {
        return preferences.getString("language", "English") ?: "English"
    }

    fun saveLanguage(language: String) {
        preferences.edit()
            .putString("language", language)
            .apply()
    }
}
