package com.apni.beyond.presentation.screens.explore

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.apni.beyond.presentation.components.PageTitle
import com.apni.beyond.presentation.components.LocalAppLanguage
import com.apni.beyond.presentation.components.appText

private data class ExploreCategory(
    val name: String,
    val indonesiaName: String,
    val description: String,
    val indonesiaDescription: String,
    val icon: String
)

@Composable
fun ExploreScreen() {
    val language = LocalAppLanguage.current

    val categories = listOf(
        ExploreCategory(
            "Planets",
            "Planet",
            "Explore worlds in our solar system.",
            "Jelajahi planet-planet di tata surya kita.",
            "🪐"
        ),
        ExploreCategory(
            "Moons",
            "Bulan",
            "Discover natural satellites.",
            "Kenali satelit alami yang mengorbit planet.",
            "🌙"
        ),
        ExploreCategory(
            "Stars",
            "Bintang",
            "Explore stars across the universe.",
            "Jelajahi bintang-bintang di alam semesta.",
            "⭐"
        ),
        ExploreCategory(
            "Galaxies",
            "Galaksi",
            "Discover enormous star systems.",
            "Temukan kumpulan bintang yang sangat luas.",
            "🌌"
        ),
        ExploreCategory(
            "Nebulae",
            "Nebula",
            "Explore cosmic clouds of gas and dust.",
            "Jelajahi awan gas dan debu di luar angkasa.",
            "☁️"
        ),
        ExploreCategory(
            "Small Bodies",
            "Benda Langit Kecil",
            "Asteroids, comets, and more.",
            "Asteroid, komet, dan benda langit lainnya.",
            "☄️"
        )
    )

    var query by remember { mutableStateOf("") }

    val filteredCategories = categories.filter { category ->
        category.name.contains(query, ignoreCase = true) ||
                category.indonesiaName.contains(query, ignoreCase = true)
    }

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
                    "Temukan hal-hal di luar dunia kita.",
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
                            "Search the universe...",
                            "Cari alam semesta...",
                            language
                        )
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )
        }

        item {
            Text(
                appText(
                    "Explore Categories",
                    "Kategori Jelajah",
                    language
                ),
                color = Color(0xFFF5F3ED),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(filteredCategories) { category ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1B2940)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = category.icon,
                        fontSize = 32.sp
                    )

                    Column {
                        Text(
                            appText(
                                category.name,
                                category.indonesiaName,
                                language
                            ),
                            color = Color(0xFFF5F3ED),
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
                            color = Color(0xFFA9B8C9),
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        if (filteredCategories.isEmpty()) {
            item {
                Text(
                    appText(
                        "No categories found.",
                        "Kategori tidak ditemukan.",
                        language
                    ),
                    color = Color(0xFFA9B8C9)
                )
            }
        }
    }
}
