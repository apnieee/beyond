package com.apni.beyond.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.apni.beyond.R

@Composable
fun PlanetVisual() {
    val infiniteTransition = rememberInfiniteTransition(
        label = "planet floating"
    )

    val offsetY by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2500,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floating"
    )

    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = "Planet Earth",
        modifier = Modifier
            .size(360.dp)
            .offset(y = offsetY.dp),
        contentScale = ContentScale.Fit
    )
}
