package com.apni.beyond

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.apni.beyond.data.repository.LanguageManager
import com.apni.beyond.presentation.components.LocalAppLanguage
import com.apni.beyond.presentation.components.appText
import com.apni.beyond.presentation.components.BeyondTheme
import com.apni.beyond.presentation.screens.home.HomeScreen
import com.apni.beyond.presentation.screens.explore.ExploreScreen
import com.apni.beyond.presentation.screens.gallery.GalleryScreen
import com.apni.beyond.presentation.screens.saved.SavedScreen
import com.apni.beyond.presentation.screens.settings.SettingsScreen

private val Navy = Color(0xFF101827)

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

    val context = LocalContext.current

    val preferences = remember {
        context.getSharedPreferences(
            "beyond_settings",
            android.content.Context.MODE_PRIVATE
        )
    }

    val languageManager = remember {
        LanguageManager(context)
    }

    var appLanguage by remember {
        mutableStateOf(languageManager.getLanguage())
    }

    var isDarkMode by remember {
        mutableStateOf(
            preferences.getBoolean("dark_mode", true)
        )
    }

    var selectedTab by remember {
        mutableIntStateOf(0)
    }

    val tabs = listOf(
        Triple(appText("Home", "Beranda", appLanguage), "⌂", 0),
        Triple(appText("Explore", "Jelajahi", appLanguage), "⌕", 1),
        Triple(appText("Gallery", "Galeri", appLanguage), "▧", 2),
        Triple(appText("Saved", "Tersimpan", appLanguage), "♡", 3),
        Triple(appText("Settings", "Pengaturan", appLanguage), "⚙", 4)
    )

    BeyondTheme(
        isDarkMode = isDarkMode
    ) {

        val cardColor = MaterialTheme.colorScheme.surface
        val textColor = MaterialTheme.colorScheme.onBackground
        val accentColor = MaterialTheme.colorScheme.primary

        CompositionLocalProvider(
            LocalAppLanguage provides appLanguage
        ) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                Crossfade(
                    targetState = isDarkMode,
                    animationSpec = tween(durationMillis = 650),
                    label = "ThemeBackground"
                ) { darkMode ->

                    if (darkMode) {

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

                    } else {

                        Image(
                            painter = painterResource(R.drawable.background2),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Transparent,

                    bottomBar = {

                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
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
                                        selectedIconColor = accentColor,
                                        selectedTextColor = accentColor,
                                        unselectedIconColor = textColor.copy(alpha = 0.6f),
                                        unselectedTextColor = textColor.copy(alpha = 0.6f),
                                        indicatorColor = cardColor
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
                                onOpenGallery = {
                                    selectedTab = 2
                                }
                            )

                            1 -> ExploreScreen()

                            2 -> GalleryScreen(
                                isDarkMode = isDarkMode
                            )

                            3 -> SavedScreen()

                            4 -> SettingsScreen(
                                language = appLanguage,

                                onLanguageChange = { newLanguage ->
                                    appLanguage = newLanguage
                                    languageManager.saveLanguage(newLanguage)
                                },

                                isDarkMode = isDarkMode,

                                onThemeChange = { darkMode ->
                                    isDarkMode = darkMode

                                    preferences.edit()
                                        .putBoolean("dark_mode", darkMode)
                                        .apply()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
