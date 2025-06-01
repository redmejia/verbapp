package com.bitinovus.verbapp.presentation.components.bubble

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryGray00
import androidx.compose.runtime.Composable

// Reply Bubble container
@Composable
fun ReplyBubble(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .background(
                color = PrimaryGray00,
                shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}