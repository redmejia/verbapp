package com.bitinovus.verbapp.presentation.viewmodel.chat

sealed class ChatState {
    abstract val chatID: String
    abstract val text: String
    abstract val userID: String
    abstract val conversationID: String

    data class Prompt(
        override val chatID: String = "",
        override val userID: String = "",
        override val conversationID: String = "",
        val timestamp: Long = 0L,
        override val text: String = "",
    ) : ChatState()

    // Reply to generated text
    data class Reply(
        override val chatID: String = "",
        override val userID: String = "",
        override val conversationID: String = "",
        val timestamp: Long = 0L,
        val replyText: String = "",
        override val text: String = "",
    ) : ChatState()

    data class Generated(
        override val chatID: String = "",
        override val userID: String = "",
        override val conversationID: String = "",
        override val text: String = "",
        val timestamp: Long = 0L,
    ) : ChatState()

    data class Loading(
        override val chatID: String = "",
        override val userID: String = "",
        override val conversationID: String = "",
        override val text: String = "",
    ) : ChatState()
}

