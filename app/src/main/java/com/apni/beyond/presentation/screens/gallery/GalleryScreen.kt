package com.apni.beyond.presentation.screens.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CardBlue = Color(0xFF1B2940)
private val White = Color(0xFFF5F3ED)
private val Muted = Color(0xFFA9B8C9)

@Composable
fun GalleryScreen() {
    val collections = listOf(
        "Earth from Space",
        "Deep Space",
        "Nebula Collection",
        "Galaxies",
        "Solar System",
        "Cosmic Wonders"
    )

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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(collections) { collection ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(135.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CardBlue
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = collection,
                            color = White,
                            fontSize = 15.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Explore collection",
                            color = Muted,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}
