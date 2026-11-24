package com.apni.beyond.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = Color(0xFF789DFF),
    background = Color(0xFF101827),
    surface = Color(0xFF1B2940),
    onBackground = Color(0xFFF5F3ED),
    onSurface = Color(0xFFF5F3ED),
    onSurfaceVariant = Color(0xFFA9B8C9)
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF4269C9),
    background = Color(0xFFF3F5FA),
    surface = Color(0xFFFFFFFF),
    onBackground = Color(0xFF17243A),
    onSurface = Color(0xFF17243A),
    onSurfaceVariant = Color(0xFF69758A)
)

@Composable
fun BeyondTheme(
    isDarkMode: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (isDarkMode) DarkColors else LightColors,
        content = content
    )
}