package com.apni.beyond.presentation.screens.saved

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.apni.beyond.presentation.viewmodel.ApodViewModel
import com.apni.beyond.presentation.viewmodel.SavedViewModel
import com.apni.beyond.presentation.components.LocalAppLanguage
import com.apni.beyond.presentation.components.appText

private val CardBlue = Color(0xFF1B2940)
private val White = Color(0xFFF5F3ED)
private val Muted = Color(0xFFA9B8C9)

@Composable
fun SavedScreen() {
    val apodViewModel: ApodViewModel = viewModel()
    val savedViewModel: SavedViewModel = viewModel()

    val language = LocalAppLanguage.current
    val apodList by apodViewModel.apodList.collectAsState()
    val savedIds by savedViewModel.savedIds.collectAsState()
    val isLoading by apodViewModel.isLoading.collectAsState()

    val savedPhotos = apodList.filter { item ->
        val id = item.postId?.toString() ?: item.date.orEmpty()
        savedIds.contains(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = appText("Saved", "Tersimpan", language),
            color = White,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = appText(
                "Your personal space collection.",
                "Koleksi luar angkasa pribadimu.",
                language
            ),
            color = Muted,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        when {
            isLoading && apodList.isEmpty() -> {
                CircularProgressIndicator()
            }

            savedPhotos.isEmpty() -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CardBlue
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = appText(
                                "Nothing saved yet",
                                "Belum ada yang tersimpan",
                                language
                            ),
                            color = White,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = appText(
                                "Save your favorite space images from Gallery to find them here.",
                                "Simpan gambar luar angkasa favoritmu dari Galeri untuk menemukannya di sini.",
                                language
                            ),
                            color = Muted,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            else -> {
                Text(
                    text = if (language == "Bahasa Indonesia") {
                        "${savedPhotos.size} gambar tersimpan"
                    } else {
                        "${savedPhotos.size} saved images"
                    },
                    color = Muted,
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

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = CardBlue
                            )
                        ) {
                            Column {
                                AsyncImage(
                                    model = item.hdUrl ?: item.url,
                                    contentDescription = item.title,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(125.dp),
                                    contentScale = ContentScale.Crop
                                )

                                Column(
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Text(
                                        text = item.title ?: "Untitled",
                                        color = White,
                                        fontSize = 13.sp,
                                        maxLines = 2
                                    )

                                    Spacer(
                                        modifier = Modifier.height(4.dp)
                                    )

                                    Text(
                                        text = item.date ?: "",
                                        color = Muted,
                                        fontSize = 11.sp
                                    )

                                    TextButton(
                                        onClick = {
                                            savedViewModel.toggleSaved(imageId)
                                        }
                                    ) {
                                        Text(
                                            text = appText(
                                                "Remove",
                                                "Hapus",
                                                language
                                            ),
                                            color = White
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
}
