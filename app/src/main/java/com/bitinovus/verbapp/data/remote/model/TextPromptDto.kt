package com.bitinovus.verbapp.data.remote.model

import com.bitinovus.verbapp.domain.model.PromptResponse
import com.google.gson.annotations.SerializedName

data class TextPromptDto(
    @SerializedName("chat_id") val chatID: String = "",
    @SerializedName("user_id") val userID: String = "",
    @SerializedName("conversation_id") val conversationID: String = "",
    @SerializedName("timestamp") val timestamp: Long = 0L,
    @SerializedName("text") val text: String = "",
)

fun TextPromptDto.toPromptResponse(): PromptResponse {
    return PromptResponse(
        chatID = chatID,
        userID = userID,
        conversationID = conversationID,
        timestamp = timestamp,
        text = text
    )
}
