package com.bitinovus.verbapp.data.remote.model

import com.bitinovus.verbapp.domain.model.ReplyResponse
import com.google.gson.annotations.SerializedName

data class ReplyTextDto(
    @SerializedName("chat_id") val chatID: String = "",
    @SerializedName("user_id") val userID: String = "",
    @SerializedName("conversation_id") val conversationID: String = "",
    @SerializedName("timestamp") val timestamp: Long = 0L,
    @SerializedName("reply") val replyText: String = "",
    @SerializedName("text") val text: String = "",
)

fun ReplyTextDto.toReplyResponse(): ReplyResponse {
    return ReplyResponse(
        chatID = chatID,
        userID = userID,
        conversationID = conversationID,
        timestamp = timestamp,
        replyText = replyText,
        text = text
    )
}