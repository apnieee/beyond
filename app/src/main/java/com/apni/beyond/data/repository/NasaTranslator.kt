package com.apni.beyond.data.repository

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await

object NasaTranslator {

    private val translator = Translation.getClient(
        TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.INDONESIAN)
            .build()
    )

    suspend fun translateToIndonesian(text: String): String {
        return try {
            translator.downloadModelIfNeeded(
                DownloadConditions.Builder().build()
            ).await()

            translator.translate(text).await()
        } catch (e: Exception) {
            text
        }
    }
}