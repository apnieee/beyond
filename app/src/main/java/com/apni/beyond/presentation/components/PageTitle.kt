package com.apni.beyond.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PageTitle(
    title: String,
    subtitle: String
) {
    Column {
        Text(
            text = title,
            color = Color(0xFFF5F3ED),
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = subtitle,
            color = Color(0xFFA9B8C9),
            fontSize = 14.sp
        )
    }
}