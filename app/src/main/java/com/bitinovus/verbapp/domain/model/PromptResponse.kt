package com.bitinovus.verbapp.domain.model

data class PromptResponse(
    val chatID: String = "",
    val userID: String = "",
    val conversationID: String = "",
    val timestamp: Long = 0L,
    val text: String = "",
)
