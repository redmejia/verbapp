package com.bitinovus.verbapp.domain.model

data class ReplyResponse(
    val chatID: String = "",
    val userID: String = "",
    val conversationID: String = "",
    val timestamp: Long = 0L,
    val replyText: String = "",
    val text: String = "",
)
