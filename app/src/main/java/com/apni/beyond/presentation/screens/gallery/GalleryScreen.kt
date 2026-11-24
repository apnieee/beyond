package com.apni.beyond.presentation.screens.gallery

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import com.apni.beyond.data.model.ApodResponse
import com.apni.beyond.presentation.components.LocalAppLanguage
import com.apni.beyond.presentation.components.appText
import com.apni.beyond.presentation.viewmodel.ApodViewModel
import com.apni.beyond.presentation.viewmodel.SavedViewModel

@Composable
fun GalleryScreen(isDarkMode: Boolean) {
    val apodViewModel: ApodViewModel = viewModel()
    val savedViewModel: SavedViewModel = viewModel()

    val language = LocalAppLanguage.current
    val savedIds by savedViewModel.savedIds.collectAsState()
    val apodList by apodViewModel.apodList.collectAsState()
    val isLoading by apodViewModel.isLoading.collectAsState()
    val errorMessage by apodViewModel.errorMessage.collectAsState()

    val textColor = MaterialTheme.colorScheme.onBackground
    val mutedColor = MaterialTheme.colorScheme.onSurfaceVariant

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
            color = textColor,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = appText(
                "Explore stunning views of the universe.",
                "Jelajahi keindahan alam semesta.",
                language
            ),
            color = mutedColor,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = appText(
                "Astronomy Collection",
                "Koleksi Astronomi",
                language
            ),
            color = textColor,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        when {
            isLoading && apodList.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
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
                        color = mutedColor
                    )

                    TextButton(
                        onClick = { apodViewModel.fetchApod() }
                    ) {
                        Text(
                            appText("Try again", "Coba lagi", language)
                        )
                    }
                }
            }

            else -> {
                val adapter = remember {
                    ApodAdapter(
                        items = emptyList(),
                        savedIds = emptySet(),
                        language = language,
                        isDarkMode = isDarkMode,
                        onSaveClick = { item ->
                            savedViewModel.toggleSaved(item)
                        },
                        onItemClick = { item ->
                            selectedImage = item
                        }
                    )
                }

                LaunchedEffect(apodList, savedIds, language, isDarkMode) {
                    adapter.updateData(
                        apodList,
                        savedIds,
                        language,
                        isDarkMode
                    )
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
                        adapter.updateData(
                            apodList,
                            savedIds,
                            language,
                            isDarkMode
                        )
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
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
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
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp
                    )

                    Text(
                        text = item.date ?: "",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )

                    TextButton(
                        onClick = {
                            selectedImage = null
                        }
                    ) {
                        Text(
                            appText("Close", "Tutup", language)
                        )
                    }
                }
            }
        }
    }
}
