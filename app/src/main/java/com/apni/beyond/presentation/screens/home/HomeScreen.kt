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
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil.compose.AsyncImage
import com.apni.beyond.presentation.viewmodel.ApodViewModel
import com.apni.beyond.data.model.ApodResponse
import com.apni.beyond.presentation.components.PageTitle
import com.apni.beyond.presentation.components.InfoCard
import com.apni.beyond.presentation.components.PlanetVisual
import com.apni.beyond.presentation.components.ApodCard

private val AccentBlue = Color(0xFF789DFF)
private val White = Color(0xFFF5F3ED)
private val Muted = Color(0xFFA9B8C9)

@Composable
fun HomeScreen(
    onOpenGallery: () -> Unit
) {
    val apodViewModel: ApodViewModel = viewModel()

    val apod by apodViewModel.apod.collectAsState()
    val isLoading by apodViewModel.isLoading.collectAsState()
    val errorMessage by apodViewModel.errorMessage.collectAsState()

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
                "Look beyond.",
                "There's a whole universe waiting to be explored."
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
                    "Earth",
                    color = White,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Our home in the universe",
                    color = Muted
                )
            }
        }

        item {
            InfoCard(
                "Explore Planet",
                "Discover planets, stars, galaxies, and more."
            )
        }

        item {
            Text(
                "Astronomy Picture of the Day",
                color = White,
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
                "Discover the universe",
                "Explore a new astronomical image and story every day."
            )
        }

        item {
            Text(
                "Today's Discovery",
                color = White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )

            InfoCard(
                "The universe is full of wonders",
                "Discover something new beyond our world."
            )
        }
    }
}
