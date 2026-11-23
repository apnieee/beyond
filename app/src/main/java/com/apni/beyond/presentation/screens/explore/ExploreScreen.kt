package com.apni.beyond.presentation.screens.explore

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.apni.beyond.presentation.components.PageTitle
import com.apni.beyond.presentation.components.InfoCard

@Composable
fun ExploreScreen() {
    val categories = listOf(
        "Planets" to "Explore worlds in our solar system.",
        "Moons" to "Discover natural satellites.",
        "Stars" to "Explore stars across the universe.",
        "Galaxies" to "Discover enormous star systems.",
        "Nebulae" to "Explore cosmic clouds of gas and dust.",
        "Small Bodies" to "Asteroids, comets, and more."
    )

    var query by remember { mutableStateOf("") }

    val filteredCategories = categories.filter {
        it.first.contains(query, ignoreCase = true)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            PageTitle(
                "Explore",
                "Discover what's beyond our world."
            )
        }

        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Search the universe...")
                },
                singleLine = true
            )
        }

        item {
            Text(
                "Explore Categories",
                color = Color(0xFFF5F3ED),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(filteredCategories) { category ->
            InfoCard(category.first, category.second)
        }

        if (filteredCategories.isEmpty()) {
            item {
                Text(
                    "No categories found.",
                    color = Color(0xFFA9B8C9)
                )
            }
        }
    }
}
