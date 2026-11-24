package com.apni.beyond.presentation.screens.saved

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.apni.beyond.presentation.viewmodel.SavedViewModel
import com.apni.beyond.presentation.components.AppCard
import com.apni.beyond.presentation.components.LocalAppLanguage
import com.apni.beyond.presentation.components.appText

@Composable
fun SavedScreen() {
    val savedViewModel: SavedViewModel = viewModel()

    val language = LocalAppLanguage.current
    val savedPhotos by savedViewModel.savedPhotos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = appText("Saved", "Tersimpan", language),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = appText(
                "Your personal space collection.",
                "Koleksi luar angkasa pribadimu.",
                language
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        when {
            savedPhotos.isEmpty() -> {
                AppCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = appText(
                            "Nothing saved yet",
                            "Belum ada yang tersimpan",
                            language
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = appText(
                            "Save your favorite space images from Gallery to find them here.",
                            "Simpan gambar luar angkasa favoritmu dari Galeri untuk menemukannya di sini.",
                            language
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }
            }

            else -> {
                Text(
                    text = if (language == "Bahasa Indonesia") {
                        "${savedPhotos.size} gambar tersimpan"
                    } else {
                        "${savedPhotos.size} saved images"
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(savedPhotos) { item ->
                        val imageId =
                            item.postId?.toString() ?: item.date.orEmpty()

                        AppCard(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = item.hdUrl ?: item.url,
                                contentDescription = item.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(125.dp),
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                modifier = Modifier.padding(2.dp)
                            ) {
                                Text(
                                    text = item.title ?: "Untitled",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 13.sp,
                                    maxLines = 2
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = item.date ?: "",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                )

                                TextButton(
                                    onClick = {
                                        savedViewModel.removeSaved(imageId)
                                    }
                                ) {
                                    Text(
                                        text = appText(
                                            "Remove",
                                            "Hapus",
                                            language
                                        ),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
