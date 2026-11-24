package com.apni.beyond.presentation.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apni.beyond.presentation.viewmodel.ApodViewModel
import com.apni.beyond.presentation.components.PageTitle
import com.apni.beyond.presentation.components.InfoCard
import com.apni.beyond.presentation.components.PlanetVisual
import com.apni.beyond.presentation.components.ApodCard
import com.apni.beyond.presentation.components.LocalAppLanguage
import com.apni.beyond.presentation.components.appText

private val AccentBlue = Color(0xFF789DFF)

@Composable
fun HomeScreen(
    onOpenGallery: () -> Unit
) {
    val apodViewModel: ApodViewModel = viewModel()

    val apod by apodViewModel.apod.collectAsState()
    val isLoading by apodViewModel.isLoading.collectAsState()
    val errorMessage by apodViewModel.errorMessage.collectAsState()

    val language = LocalAppLanguage.current

    val textColor = MaterialTheme.colorScheme.onBackground
    val mutedColor = MaterialTheme.colorScheme.onSurfaceVariant

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                "BEYOND.",
                color = AccentBlue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            PageTitle(
                appText("Look beyond.", "Lihat lebih jauh.", language),
                appText(
                    "There's a whole universe waiting to be explored.",
                    "Ada seluruh alam semesta yang menunggu untuk dijelajahi.",
                    language
                )
            )
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(390.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(350.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0x553D75FF),
                                    Color(0x223D75FF),
                                    Color.Transparent
                                )
                            ),
                            CircleShape
                        )
                )

                Box(
                    modifier = Modifier
                        .size(370.dp)
                        .border(
                            1.dp,
                            Color(0x33789DFF),
                            CircleShape
                        )
                )

                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .border(
                            1.dp,
                            Color(0x22789DFF),
                            CircleShape
                        )
                )

                PlanetVisual()
            }
        }

        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    appText("Earth", "Bumi", language),
                    color = textColor,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    appText(
                        "Our home in the universe",
                        "Rumah kita di alam semesta",
                        language
                    ),
                    color = mutedColor
                )
            }
        }

        item {
            InfoCard(
                appText("Explore Planet", "Jelajahi Planet", language),
                appText(
                    "Discover planets, stars, galaxies, and more.",
                    "Temukan planet, bintang, galaksi, dan lainnya.",
                    language
                )
            )
        }

        item {
            Text(
                appText(
                    "Astronomy Picture of the Day",
                    "Gambar Astronomi Hari Ini",
                    language
                ),
                color = textColor,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            ApodCard(
                apod = apod,
                isLoading = isLoading,
                errorMessage = errorMessage,
                onRetry = { apodViewModel.fetchApod() },
                onClick = onOpenGallery
            )
        }

        item {
            InfoCard(
                appText(
                    "Discover the universe",
                    "Temukan Alam Semesta",
                    language
                ),
                appText(
                    "Explore a new astronomical image and story every day.",
                    "Jelajahi gambar dan kisah astronomi baru setiap hari.",
                    language
                )
            )
        }

        item {
            Text(
                appText(
                    "Today's Discovery",
                    "Penemuan Hari Ini",
                    language
                ),
                color = textColor,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            InfoCard(
                appText(
                    "The universe is full of wonders",
                    "Alam semesta penuh keajaiban",
                    language
                ),
                appText(
                    "Discover something new beyond our world.",
                    "Temukan hal baru di luar dunia kita.",
                    language
                )
            )
        }
    }
}
