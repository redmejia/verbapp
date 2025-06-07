package com.bitinovus.verbapp.presentation.viewmodel.chat

sealed class ChatState {
    // abstract val subjectType: String
    abstract val chatID: String
    abstract val text: String
    abstract val userID: String
    abstract val conversationID: String
    // abstract val replyText: String

    data class Prompt(
        // override val subjectType: String = "Sender",
        override val chatID: String = "",
        override val userID: String = "",
        override val conversationID: String = "",
        val timestamp: Long = 0L,
        override val text: String = "",
        //val replyText: String = "",
    ) : ChatState()

    // Reply to generated text
    data class Reply(
        // override val subjectType: String = "Sender",
        override val chatID: String = "",
        override val userID: String = "",
        override val conversationID: String = "",
        override val text: String = "",
        val replyText: String = "",
    ) : ChatState()

    data class Generated(
        // override val subjectType: String = "Generated",
        override val chatID: String = "",
        override val userID: String = "",
        override val conversationID: String = "",
        override val text: String = "",

        ) : ChatState()

    data class Loading(
        override val chatID: String = "",
        override val userID: String = "",
        override val conversationID: String = "",
        override val text: String = "",
    ) : ChatState()
}


//sealed class ChatMessage {
//    abstract val chatID: String
//    abstract val timestamp: Long
//
//    data class Sent(
//        override val chatID: String,
//        val text: String,
//        override val timestamp: Long
//    ) : ChatMessage()
//
//    data class Received(
//        override val chatID: String,
//        val text: String,
//        override val timestamp: Long
//    ) : ChatMessage()
//
//    data class Reply(
//        override val chatID: String,
//        val replyToText: String,
//        val text: String,
//        override val timestamp: Long
//    ) : ChatMessage()
//
//    data class Loading(
//        override val chatID: String = UUID.randomUUID().toString(),
//        override val timestamp: Long = System.currentTimeMillis()
//    ) : ChatMessage()
//}
