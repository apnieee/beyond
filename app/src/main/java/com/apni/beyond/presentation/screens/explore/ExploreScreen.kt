package com.apni.beyond.presentation.screens.explore

import androidx.core.text.HtmlCompat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.apni.beyond.data.model.NasaItem
import com.apni.beyond.data.repository.NasaTranslator
import com.apni.beyond.presentation.components.AppCard
import com.apni.beyond.presentation.components.LocalAppLanguage
import com.apni.beyond.presentation.components.PageTitle
import com.apni.beyond.presentation.components.appText
import com.apni.beyond.presentation.viewmodel.ExploreViewModel

private data class ExploreCategory(
    val name: String,
    val indonesiaName: String,
    val description: String,
    val indonesiaDescription: String,
    val apiQuery: String
)

private fun cleanHtml(html: String): String {
    return HtmlCompat.fromHtml(
        html,
        HtmlCompat.FROM_HTML_MODE_COMPACT
    ).toString().replace(Regex("\\s+"), " ").trim()
}

@Composable
fun ExploreScreen(
    exploreViewModel: ExploreViewModel = viewModel()
) {
    val language = LocalAppLanguage.current

    val objects by exploreViewModel.objects.collectAsState()
    val isLoading by exploreViewModel.isLoading.collectAsState()
    val errorMessage by exploreViewModel.errorMessage.collectAsState()

    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var selectedItem by remember { mutableStateOf<NasaItem?>(null) }

    fun performSearch() {
        val searchQuery = query.trim()

        if (searchQuery.isNotEmpty()) {
            selectedCategory = searchQuery
            exploreViewModel.searchObjects(searchQuery)
        }
    }

    if (selectedItem != null) {
        ExploreDetailScreen(
            item = selectedItem!!,
            language = language,
            onBack = { selectedItem = null }
        )
        return
    }

    val categories = listOf(
        ExploreCategory(
            "Planets", "Planet",
            "Explore worlds in our solar system.",
            "Jelajahi planet di tata surya.",
            "planet"
        ),
        ExploreCategory(
            "Moons", "Bulan",
            "Discover natural satellites.",
            "Kenali satelit alami.",
            "moon"
        ),
        ExploreCategory(
            "Stars", "Bintang",
            "Explore stars across the universe.",
            "Jelajahi bintang di alam semesta.",
            "star"
        ),
        ExploreCategory(
            "Galaxies", "Galaksi",
            "Discover enormous star systems.",
            "Temukan kumpulan bintang yang luas.",
            "galaxy"
        ),
        ExploreCategory(
            "Nebulae", "Nebula",
            "Explore cosmic clouds of gas and dust.",
            "Jelajahi awan gas dan debu kosmik.",
            "nebula"
        ),
        ExploreCategory(
            "Small Bodies", "Benda Langit Kecil",
            "Asteroids, comets, and more.",
            "Asteroid, komet, dan lainnya.",
            "asteroid comet"
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            PageTitle(
                appText("Explore", "Jelajahi", language),
                appText(
                    "Discover what's beyond our world.",
                    "Temukan benda-benda di luar dunia kita.",
                    language
                )
            )
        }

        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        appText(
                            "Search space objects...",
                            "Cari benda langit...",
                            language
                        )
                    )
                },
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        performSearch()
                    }
                )
            )

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { performSearch() },
                modifier = Modifier.fillMaxWidth(),
                enabled = query.isNotBlank()
            ) {
                Text(appText("Search", "Cari", language))
            }
        }

        item {
            Text(
                appText("Explore Categories", "Kategori Jelajah", language),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(categories) { category ->
            AppCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        query = appText(
                            category.name,
                            category.indonesiaName,
                            language
                        )
                        selectedCategory = category.apiQuery
                        exploreViewModel.searchObjects(category.apiQuery)
                    }
            ) {
                Text(
                    appText(
                        category.name,
                        category.indonesiaName,
                        language
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    appText(
                        category.description,
                        category.indonesiaDescription,
                        language
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )
            }
        }

        if (selectedCategory.isNotBlank()) {
            item {
                Text(
                    appText("Search Results", "Hasil Pencarian", language),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (isLoading) {
            item {
                CircularProgressIndicator()
            }
        }

        errorMessage?.let { error ->
            item {
                Text(
                    error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        items(objects) { item ->
            val data = item.data?.firstOrNull()
            val imageUrl = item.links
                ?.firstOrNull { it.render == "image" }
                ?.href

            val description = cleanHtml(data?.description.orEmpty())

            AppCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedItem = item
                    }
            ) {
                if (!imageUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = data?.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(190.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(Modifier.height(10.dp))
                }

                Text(
                    data?.title ?: "Unknown Object",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    description.take(180).let {
                        if (description.length > 180) "$it..." else it
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    appText("See more →", "Lihat selengkapnya →", language),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        if (
            selectedCategory.isNotBlank() &&
            !isLoading &&
            errorMessage == null &&
            objects.isEmpty()
        ) {
            item {
                Text(
                    appText(
                        "No results found.",
                        "Tidak ada hasil ditemukan.",
                        language
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ExploreDetailScreen(
    item: NasaItem,
    language: String,
    onBack: () -> Unit
) {
    val data = item.data?.firstOrNull()
    val imageUrl = item.links
        ?.firstOrNull { it.render == "image" }
        ?.href

    val originalDescription = remember(data?.description) {
        cleanHtml(data?.description.orEmpty())
    }

    var translatedDescription by remember(item, language) {
        mutableStateOf(originalDescription)
    }

    var isTranslating by remember(item, language) {
        mutableStateOf(false)
    }

    LaunchedEffect(item, language) {
        if (language == "Bahasa Indonesia" && originalDescription.isNotBlank()) {
            isTranslating = true

            translatedDescription = NasaTranslator.translateToIndonesian(
                originalDescription.take(3500)
            )

            isTranslating = false
        } else {
            translatedDescription = originalDescription
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        TextButton(onClick = onBack) {
            Text(
                appText("← Back to Explore", "← Kembali ke Jelajahi", language)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                if (!imageUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = data?.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            item {
                Text(
                    data?.title ?: "Unknown Object",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Text(
                    appText("About this object", "Tentang benda langit ini", language),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                when {
                    isTranslating -> {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                            Text(
                                appText(
                                    "Translating description...",
                                    "Menerjemahkan penjelasan...",
                                    language
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    translatedDescription.isBlank() -> {
                        Text(
                            appText(
                                "No description available.",
                                "Penjelasan belum tersedia.",
                                language
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    else -> {
                        Text(
                            translatedDescription,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 15.sp,
                            lineHeight = 24.sp
                        )
                    }
                }
            }

            data?.dateCreated?.let { date ->
                item {
                    Text(
                        appText("Published: ", "Dipublikasikan: ", language) +
                                date.take(10),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
