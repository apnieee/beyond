package com.apni.beyond.presentation.screens.saved

import androidx.compose.foundation.clickable
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

private val CardBlue = Color(0xFF1B2940)
private val White = Color(0xFFF5F3ED)
private val Muted = Color(0xFFA9B8C9)

@Composable
fun SavedScreen() {
    val apodViewModel: ApodViewModel = viewModel()
    val savedViewModel: SavedViewModel = viewModel()

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
            text = "Saved",
            color = White,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your personal space collection.",
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
                            text = "Nothing saved yet",
                            color = White,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Save your favorite space images from Gallery to find them here.",
                            color = Muted,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            else -> {
                Text(
                    text = "${savedPhotos.size} saved images",
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
                                            text = "Remove",
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
