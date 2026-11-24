package com.apni.beyond.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.apni.beyond.data.model.ApodResponse
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await

@Composable
fun ApodCard(
    apod: ApodResponse?,
    isLoading: Boolean,
    errorMessage: String?,
    onRetry: () -> Unit,
    onClick: () -> Unit
) {
    val language = LocalAppLanguage.current

    var expanded by remember { mutableStateOf(false) }
    var translatedText by remember { mutableStateOf<String?>(null) }
    var isTranslating by remember { mutableStateOf(false) }
    var translationFailed by remember { mutableStateOf(false) }

    val originalText = remember(apod?.explanation) {
        HtmlCompat.fromHtml(
            apod?.explanation ?: "No description available.",
            HtmlCompat.FROM_HTML_MODE_COMPACT
        ).toString()
    }

    LaunchedEffect(originalText, language) {
        translatedText = null
        translationFailed = false

        if (language == "Bahasa Indonesia" && apod?.explanation != null) {
            isTranslating = true

            val options = TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.INDONESIAN)
                .build()

            val translator = Translation.getClient(options)

            try {
                translator.downloadModelIfNeeded().await()
                translatedText = translator.translate(originalText).await()
            } catch (e: Exception) {
                translationFailed = true
            } finally {
                translator.close()
                isTranslating = false
            }
        }
    }

    val description = if (language == "Bahasa Indonesia") {
        translatedText ?: originalText
    } else {
        originalText
    }

    AppCard(modifier = Modifier.fillMaxWidth()) {
        when {
            apod != null -> {
                Text(
                    text = apod.title ?: "Astronomy Picture of the Day",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(Modifier.height(10.dp))

                when (apod.mediaType) {
                    "image" -> {
                        AsyncImage(
                            model = apod.hdUrl ?: apod.url,
                            contentDescription = apod.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clickable { onClick() },
                            contentScale = ContentScale.Crop
                        )
                    }

                    "video" -> {
                        Text(
                            text = if (language == "Bahasa Indonesia")
                                "Penemuan hari ini berupa video."
                            else
                                "Today's discovery is a video.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    else -> {
                        Text(
                            text = if (language == "Bahasa Indonesia")
                                "Media tidak tersedia."
                            else
                                "Media is currently unavailable.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = apod.date ?: "Explore the universe",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 13.sp,
                    maxLines = if (expanded) Int.MAX_VALUE else 5,
                    overflow = TextOverflow.Ellipsis
                )

                if (isTranslating) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Menerjemahkan...",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }

                if (translationFailed) {
                    Text(
                        "Terjemahan gagal. Menampilkan teks asli.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }

                TextButton(
                    onClick = { expanded = !expanded }
                ) {
                    Text(
                        if (language == "Bahasa Indonesia") {
                            if (expanded) "Lebih sedikit" else "Selengkapnya"
                        } else {
                            if (expanded) "See less" else "See more"
                        }
                    )
                }
            }

            isLoading -> {
                CircularProgressIndicator()
                Text(
                    if (language == "Bahasa Indonesia")
                        "Memuat data astronomi..."
                    else
                        "Loading astronomy data...",
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            errorMessage != null -> {
                Text(
                    errorMessage,
                    color = MaterialTheme.colorScheme.onSurface
                )

                TextButton(onClick = onRetry) {
                    Text(
                        if (language == "Bahasa Indonesia")
                            "Coba lagi"
                        else
                            "Try again"
                    )
                }
            }
        }
    }
}
