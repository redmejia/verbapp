package com.bitinovus.verbapp.data.remote.model

import com.bitinovus.verbapp.domain.model.ChatsResponse
import com.google.gson.annotations.SerializedName

data class ChatsDto(
    @SerializedName("prompt") val promptDto: TextPromptDto,
    @SerializedName("generated_text") val generatedTextDto : GeneratedTextDto
)

fun List<ChatsDto>.toChatResponseList() : List<ChatsResponse> {
    return this.map {
        ChatsResponse(
            prompt = it.promptDto.toPromptResponse(),
            generatedResponse = it.generatedTextDto.toGeneratedResponse()
        )
    }
}
