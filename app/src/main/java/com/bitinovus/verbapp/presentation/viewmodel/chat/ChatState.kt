package com.bitinovus.verbapp.presentation.viewmodel.chat

sealed class ChatState {
    // abstract val subjectType: String
    abstract val chatID: String
    abstract val text: String

    data class Prompt(
        // override val subjectType: String = "Sender",
        override val chatID: String = "",
        override val text: String = "",
    ) : ChatState()

    data class Generated(
        // override val subjectType: String = "Generated",
        override val chatID: String = "",
        override val text: String = "",
    ) : ChatState()
}



