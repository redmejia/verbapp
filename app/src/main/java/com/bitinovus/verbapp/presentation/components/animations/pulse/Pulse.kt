package com.bitinovus.verbapp.presentation.components.animations.pulse

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryPurple40
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryPurple80

@Composable
fun Pulse(
    dimension: PulseDimension = PulseDimension(),
    pulsarRadius: Float = 60f,
    outerColor: Color = PrimaryPurple40,
    innerColor: Color = PrimaryPurple80,
) {
    val floatOuterActionSize by remember {
        mutableStateOf(IntSize(dimension.outerDimensionSize.first, dimension.outerDimensionSize.second))
    }
    val floatInnerActionSize by remember {
        mutableStateOf(IntSize(dimension.innerDimensionSize.first, dimension.innerDimensionSize.second))
    }


    val (radius, alpha) = pulsarBuilder(
        pulsarRadius = pulsarRadius,
        floatActionSize = floatOuterActionSize.width
    )

    val (innerCircleRadius, innerCircleAlpha) = pulsarBuilder(
        pulsarRadius = pulsarRadius,
        floatActionSize = floatInnerActionSize.width
    )

    Box(
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier
                .matchParentSize(),
            onDraw = {
                drawCircle(
                    color = outerColor,
                    radius = radius,
                    alpha = alpha
                )
            })

        Canvas(
            modifier = Modifier
                .padding(pulsarRadius.dp),
            onDraw = {
                drawCircle(
                    color = innerColor,
                    radius = innerCircleRadius,
                    alpha = innerCircleAlpha
                )
            })
    }
}