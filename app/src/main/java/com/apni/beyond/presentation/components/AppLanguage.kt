package com.apni.beyond.presentation.components

import androidx.compose.runtime.compositionLocalOf

val LocalAppLanguage = compositionLocalOf {
    "English"
}

fun appText(
    english: String,
    indonesia: String,
    language: String
): String {
    return if (language == "Bahasa Indonesia") {
        indonesia
    } else {
        english
    }
}