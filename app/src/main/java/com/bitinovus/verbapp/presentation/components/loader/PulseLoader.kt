package com.bitinovus.verbapp.presentation.components.loader

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bitinovus.verbapp.presentation.components.animations.pulse.Pulse
import com.bitinovus.verbapp.presentation.components.animations.pulse.PulseDimension
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryPurple40
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryPurple80


@Composable
fun PulseLoader(
    dimension: PulseDimension = PulseDimension(),
    pulsarRadius: Float = 60f,
    outerColor: Color = PrimaryPurple40,
    innerColor: Color = PrimaryPurple80,
    isLoading: Boolean = false,
) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Pulse(
                dimension = dimension,
                pulsarRadius = pulsarRadius,
                outerColor = outerColor,
                innerColor = innerColor
            )
        }
    }
}