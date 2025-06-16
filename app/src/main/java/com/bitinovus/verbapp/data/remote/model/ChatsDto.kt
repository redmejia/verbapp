package com.bitinovus.verbapp.data.remote.model

import com.bitinovus.verbapp.domain.model.ChatsResponse
import com.google.gson.annotations.SerializedName

data class ChatsDto(
    @SerializedName("prompt") val promptDto: TextPromptDto,
    @SerializedName("reply_text") val replyTextDto: ReplyTextDto,
    @SerializedName("generated_text") val generatedTextDto: GeneratedTextDto,
)

fun List<ChatsDto>.toChatResponseList(): List<ChatsResponse> {
    return this.map {
        ChatsResponse(
            prompt = it.promptDto.toPromptResponse(),
            reply = it.replyTextDto.toReplyResponse(),
            generatedResponse = it.generatedTextDto.toGeneratedResponse()
        )
    }
}
