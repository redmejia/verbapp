package com.bitinovus.verbapp.presentation.screens.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Chat(
    contentPadding: PaddingValues,
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    Box(
        modifier = Modifier
            .systemBarsPadding()
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.statusBars)
                .imePadding(),
            contentPadding = contentPadding,
            reverseLayout = true
        ) {
            items(count = 130) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = "Item $it"
                )
            }
        }
        Box(
            modifier = Modifier
                .imePadding()
                .matchParentSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                placeholder = { Text(text = "Message...") }
            )
        }
    }
}
