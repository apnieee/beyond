package com.apni.beyond

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.apni.beyond.presentation.screens.home.HomeScreen
import com.apni.beyond.presentation.screens.explore.ExploreScreen
import com.apni.beyond.presentation.screens.gallery.GalleryScreen
import com.apni.beyond.presentation.screens.saved.SavedScreen
import com.apni.beyond.presentation.screens.settings.SettingsScreen

private val Navy = Color(0xFF101827)
private val CardBlue = Color(0xFF1B2940)
private val AccentBlue = Color(0xFF789DFF)
private val White = Color(0xFFF5F3ED)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BeyondApp()
        }
    }
}

@Composable
fun BeyondApp() {
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        Triple("Home", "⌂", 0),
        Triple("Explore", "⌕", 1),
        Triple("Gallery", "▧", 2),
        Triple("Saved", "♡", 3),
        Triple("Settings", "⚙", 4)
    )

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = AccentBlue,
            background = Navy,
            surface = CardBlue
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Navy.copy(alpha = 0.55f))
            )

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.Transparent,

                bottomBar = {
                    NavigationBar(
                        containerColor = Navy.copy(alpha = 0.95f),
                        tonalElevation = 0.dp
                    ) {
                        tabs.forEach { tab ->
                            NavigationBarItem(
                                selected = selectedTab == tab.third,
                                onClick = {
                                    selectedTab = tab.third
                                },
                                icon = {
                                    Text(
                                        text = tab.second,
                                        fontSize = 21.sp
                                    )
                                },
                                label = {
                                    Text(
                                        text = tab.first,
                                        fontSize = 10.sp
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = AccentBlue,
                                    selectedTextColor = AccentBlue,
                                    unselectedIconColor = White.copy(alpha = 0.6f),
                                    unselectedTextColor = White.copy(alpha = 0.6f),
                                    indicatorColor = CardBlue
                                )
                            )
                        }
                    }
                }
            ) { innerPadding ->

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (selectedTab) {
                        0 -> HomeScreen(
                            onOpenGallery = { selectedTab = 2 }
                        )
                        1 -> ExploreScreen()
                        2 -> GalleryScreen()
                        3 -> SavedScreen()
                        4 -> SettingsScreen()
                    }
                }
            }
        }
    }
}