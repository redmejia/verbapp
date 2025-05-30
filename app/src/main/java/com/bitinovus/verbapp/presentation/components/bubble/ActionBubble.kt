package com.bitinovus.verbapp.presentation.components.bubble

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// container
@Composable
fun ActionBubble(
    modifier: Modifier = Modifier,
    content: @Composable (RowScope.() -> Unit),
) {
    Row(modifier = modifier) {
        content()
    }
}

@Composable
fun CopyActionButton(
    unselectedImage: Int,
    selectedImage: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    var isPressed by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    IconButton(
        modifier = modifier,
        onClick = {
            isPressed = true
            onClick()
            scope.launch {
                delay(1000L)
                isPressed = false
            }
        },
    ) {
        Icon(
            painter = if (isPressed) painterResource(id = selectedImage)
            else painterResource(id = unselectedImage),
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun SaveActionButton(
    selectedImage: Int,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(id = selectedImage),
            contentDescription = contentDescription,
        )
    }
}
