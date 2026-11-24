package com.apni.beyond.presentation.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apni.beyond.presentation.components.LocalAppLanguage
import com.apni.beyond.presentation.components.appText

@Composable
fun SettingsScreen(
    language: String,
    onLanguageChange: (String) -> Unit,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    var animation by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }

    val currentLanguage = LocalAppLanguage.current
    val textColor = MaterialTheme.colorScheme.onBackground
    val mutedColor = MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = appText("Settings", "Pengaturan", currentLanguage),
            color = textColor,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = appText("Language", "Bahasa", currentLanguage),
            color = textColor,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box {
            OutlinedButton(
                onClick = { expanded = true }
            ) {
                Text(language)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("English") },
                    onClick = {
                        onLanguageChange("English")
                        expanded = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("Bahasa Indonesia") },
                    onClick = {
                        onLanguageChange("Bahasa Indonesia")
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SettingSwitch(
            title = appText(
                "Dark Mode",
                "Mode Gelap",
                currentLanguage
            ),
            checked = isDarkMode,
            onCheckedChange = onThemeChange
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingSwitch(
            title = appText(
                "Floating Animation",
                "Animasi Mengambang",
                currentLanguage
            ),
            checked = animation,
            onCheckedChange = { animation = it }
        )

        Spacer(modifier = Modifier.height(28.dp))

        HorizontalDivider(
            color = mutedColor.copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = appText(
                "About Beyond",
                "Tentang Beyond",
                currentLanguage
            ),
            color = textColor,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = appText(
                "BEYOND — Explore the universe.",
                "BEYOND — Jelajahi alam semesta.",
                currentLanguage
            ),
            color = mutedColor,
            fontSize = 14.sp
        )

        Text(
            text = appText(
                "A space observatory application.",
                "Aplikasi observatorium luar angkasa.",
                currentLanguage
            ),
            color = mutedColor,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun SettingSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 15.sp
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
