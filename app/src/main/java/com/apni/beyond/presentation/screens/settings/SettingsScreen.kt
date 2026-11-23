package com.apni.beyond.presentation.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CardBlue = Color(0xFF1B2940)
private val White = Color(0xFFF5F3ED)
private val Muted = Color(0xFFA9B8C9)

@Composable
fun SettingsScreen() {
    var language by remember { mutableStateOf("English") }
    var darkMode by remember { mutableStateOf(true) }
    var animation by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Settings",
            color = White,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Language", color = White, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(8.dp))

        var expanded by remember { mutableStateOf(false) }

        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(language)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("English") },
                    onClick = {
                        language = "English"
                        expanded = false
                    }
                )

                DropdownMenuItem(
                    text = { Text("Bahasa Indonesia") },
                    onClick = {
                        language = "Bahasa Indonesia"
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        SettingSwitch(
            title = "Dark Mode",
            checked = darkMode,
            onCheckedChange = { darkMode = it }
        )

        Spacer(modifier = Modifier.height(12.dp))

        SettingSwitch(
            title = "Floating Animation",
            checked = animation,
            onCheckedChange = { animation = it }
        )

        Spacer(modifier = Modifier.height(28.dp))

        HorizontalDivider(color = Muted.copy(alpha = 0.3f))

        Spacer(modifier = Modifier.height(20.dp))

        Text("About Beyond", color = White, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "BEYOND — Explore the universe.",
            color = Muted,
            fontSize = 14.sp
        )

        Text(
            text = "A space observatory application.",
            color = Muted,
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
        Text(title, color = White, fontSize = 15.sp)

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
