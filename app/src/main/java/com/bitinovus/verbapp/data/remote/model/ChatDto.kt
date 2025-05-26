package com.bitinovus.verbapp.data.remote.model

import com.bitinovus.verbapp.domain.model.Response
import com.bitinovus.verbapp.domain.model.ResponseMetadata
import com.google.gson.annotations.SerializedName

data class GeneratedText(
    @SerializedName("chat_id") val chatID: String = "",
    @SerializedName("user_id") val userID: String = "",
    @SerializedName("timestamp") val timestamp: Long = 0L,
    @SerializedName("text") val text: String = "", // generated text
    @SerializedName("metadata") val metadata: Metadata = Metadata(modelName = "v0"),
)

data class Metadata(
    @SerializedName("model_name") val modelName: String,
)

// DTO to domain model
fun GeneratedText.toResponse(): Response {
    return Response(
        chatID = chatID,
        userID = userID,
        timestamp = timestamp,
        text = text,
        metadata = ResponseMetadata(modelName = metadata.modelName)
    )
}
