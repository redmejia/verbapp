package com.bitinovus.verbapp.presentation.components.prompt

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryBlack00
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryGray00

@Composable
fun PromptTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    placeHolder: @Composable () -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = PrimaryGray00,
            unfocusedContainerColor = PrimaryGray00,
            focusedTextColor = PrimaryBlack00,
            focusedIndicatorColor = PrimaryGray00,
            unfocusedIndicatorColor = PrimaryGray00
        ),
        value = value,
        onValueChange = onValueChange,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        placeholder = { placeHolder() },
    )
}