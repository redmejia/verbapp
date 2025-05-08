package com.bitinovus.verbapp.presentation.components.prompt

import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryGray00
import com.bitinovus.verbapp.presentation.ui.theme.TextColor

@Composable
fun PromptTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: @Composable () -> Unit,
    trailingIcon: @Composable () -> Unit,
) {
    TextField(
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = PrimaryGray00,
            unfocusedContainerColor = PrimaryGray00,
            focusedTextColor = TextColor,
            focusedIndicatorColor = PrimaryGray00,
            unfocusedIndicatorColor = PrimaryGray00
        ),
        value = value,
        onValueChange = onValueChange,
        placeholder = { placeHolder() },
        trailingIcon = { trailingIcon() }
    )
}