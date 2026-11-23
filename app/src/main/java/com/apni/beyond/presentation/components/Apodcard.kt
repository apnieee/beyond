package com.apni.beyond.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.clickable
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import androidx.compose.ui.text.style.TextOverflow
import com.apni.beyond.data.model.ApodResponse

@Composable
fun ApodCard(
    apod: ApodResponse?,
    isLoading: Boolean,
    errorMessage: String?,
    onRetry: () -> Unit,
    onClick: () -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1B2940)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            when {
                apod != null -> {
                    Text(
                        apod.title ?: "Astronomy Picture of the Day",
                        fontSize = 18.sp,
                        color = Color.White
                    )

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
                                text = "Today's discovery is a video.",
                                color = Color.LightGray
                            )
                        }

                        else -> {
                            Text(
                                "Media is currently unavailable.",
                                color = Color.LightGray
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        apod.date ?: "Explore the universe",
                        color = Color.LightGray
                    )

                    Text(
                        text = HtmlCompat.fromHtml(
                            apod.explanation ?: "No description available.",
                            HtmlCompat.FROM_HTML_MODE_COMPACT
                        ).toString(),
                        color = Color.White,
                        fontSize = 13.sp,
                        maxLines = if (expanded.value) Int.MAX_VALUE else 5,
                        overflow = TextOverflow.Ellipsis
                    )

                    TextButton(
                        onClick = {
                            expanded.value = !expanded.value
                        }
                    ) {
                        Text(if (expanded.value) "See less" else "See more")
                    }
                }

                isLoading -> {
                    CircularProgressIndicator()
                    Text("Loading astronomy data...")
                }

                errorMessage != null -> {
                    Text(
                        errorMessage ?: "Unknown error",
                        color = Color.LightGray
                    )

                    TextButton(onClick = onRetry) {
                        Text("Try again")
                    }
                }
            }
        }
    }
}