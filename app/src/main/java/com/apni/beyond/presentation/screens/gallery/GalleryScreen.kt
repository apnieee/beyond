package com.apni.beyond.presentation.screens.gallery

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apni.beyond.data.model.ApodResponse
import com.apni.beyond.presentation.components.LocalAppLanguage
import com.apni.beyond.presentation.components.appText
import com.apni.beyond.presentation.viewmodel.ApodViewModel
import com.apni.beyond.presentation.viewmodel.SavedViewModel

private val White = Color(0xFFF5F3ED)
private val Muted = Color(0xFFA9B8C9)

@Composable
fun GalleryScreen() {
    val apodViewModel: ApodViewModel = viewModel()
    val savedViewModel: SavedViewModel = viewModel()

    val language = LocalAppLanguage.current
    val savedIds by savedViewModel.savedIds.collectAsState()
    val apodList by apodViewModel.apodList.collectAsState()
    val isLoading by apodViewModel.isLoading.collectAsState()
    val errorMessage by apodViewModel.errorMessage.collectAsState()

    var selectedImage by remember {
        mutableStateOf<ApodResponse?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = appText("Gallery", "Galeri", language),
            color = White,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = appText(
                "Explore stunning views of the universe.",
                "Jelajahi keindahan alam semesta.",
                language
            ),
            color = Muted,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = appText(
                "Astronomy Collection",
                "Koleksi Astronomi",
                language
            ),
            color = White,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        when {
            isLoading && apodList.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null && apodList.isEmpty() -> {
                Column {
                    Text(
                        text = appText(
                            "Failed to load images.",
                            "Gagal memuat gambar.",
                            language
                        ),
                        color = Muted
                    )

                    TextButton(
                        onClick = { apodViewModel.fetchApod() }
                    ) {
                        Text(
                            appText(
                                "Try again",
                                "Coba lagi",
                                language
                            )
                        )
                    }
                }
            }

            else -> {
                val adapter = remember {
                    ApodAdapter(
                        items = emptyList(),
                        savedIds = emptySet(),
                        onSaveClick = { item ->
                            val id = item.postId?.toString()
                                ?: item.date.orEmpty()

                            if (id.isNotEmpty()) {
                                savedViewModel.toggleSaved(id)
                            }
                        },
                        onItemClick = { item ->
                            selectedImage = item
                        }
                    )
                }

                LaunchedEffect(apodList, savedIds) {
                    adapter.updateData(apodList, savedIds)
                }

                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    factory = { context ->
                        RecyclerView(context).apply {
                            layoutManager = GridLayoutManager(context, 2)
                            this.adapter = adapter
                            setHasFixedSize(false)
                            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                        }
                    },
                    update = {
                        adapter.updateData(apodList, savedIds)
                    }
                )
            }
        }
    }

    selectedImage?.let { item ->
        Dialog(
            onDismissRequest = {
                selectedImage = null
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
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
                        .heightIn(max = 600.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = item.title ?: appText(
                        "Astronomy Picture",
                        "Gambar Astronomi",
                        language
                    ),
                    color = White,
                    fontSize = 16.sp
                )

                Text(
                    text = item.date ?: "",
                    color = Muted,
                    fontSize = 12.sp
                )

                TextButton(
                    onClick = { selectedImage = null }
                ) {
                    Text(
                        appText("Close", "Tutup", language)
                    )
                }
            }
        }
    }
}
