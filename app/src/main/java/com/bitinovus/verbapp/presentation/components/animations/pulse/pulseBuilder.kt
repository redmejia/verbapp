package com.bitinovus.verbapp.presentation.components.animations.pulse

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

@Composable
fun pulsarBuilder(pulsarRadius: Float, floatActionSize: Int): Pair<Float, Float> {

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val radius by infiniteTransition.animateFloat(
        initialValue = (floatActionSize / 2).toFloat(),
        targetValue = floatActionSize + pulsarRadius,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(3000),
            repeatMode = RepeatMode.Restart
        ),
        label = "radius"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(3000),
            initialStartOffset = StartOffset(100), // prevent a graphic issue when the animation restart
            repeatMode = RepeatMode.Restart
        ),
        label = "alpha"
    )

    val pulsarPair: Pair<Float, Float> = Pair(radius, alpha)

    return pulsarPair

}

data class PulseDimension(
    val outerActionSize: Pair<Int, Int> = Pair(20, 20),
    val innerActionSize: Pair<Int, Int> = Pair(45, 45),
)