
package com.apni.beyond.presentation.screens.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.apni.beyond.presentation.viewmodel.ApodViewModel

private val CardBlue = Color(0xFF1B2940)
private val White = Color(0xFFF5F3ED)
private val Muted = Color(0xFFA9B8C9)

@Composable
fun GalleryScreen() {
    val apodViewModel: ApodViewModel = viewModel()
    val apodList by apodViewModel.apodList.collectAsState()
    val isLoading by apodViewModel.isLoading.collectAsState()
    val errorMessage by apodViewModel.errorMessage.collectAsState()

    var selectedImage by remember { mutableStateOf<com.apni.beyond.data.model.ApodResponse?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Gallery",
            color = White,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Explore stunning views of the universe.",
            color = Muted,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Astronomy Collection",
            color = White,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        when {
            isLoading && apodList.isEmpty() -> {
                CircularProgressIndicator()
            }

            errorMessage != null && apodList.isEmpty() -> {
                Text(errorMessage ?: "Failed to load images.", color = Muted)
                TextButton(onClick = { apodViewModel.fetchApod() }) {
                    Text("Try again")
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(apodList) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedImage = item },
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

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = item.date ?: "",
                                        color = Muted,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    selectedImage?.let { item ->
        Dialog(
            onDismissRequest = { selectedImage = null },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { selectedImage = null },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    AsyncImage(
                        model = item.hdUrl ?: item.url,
                        contentDescription = item.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 600.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = item.title ?: "Astronomy Picture",
                        color = White,
                        fontSize = 16.sp
                    )

                    Text(
                        text = item.date ?: "",
                        color = Muted,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
