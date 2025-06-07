package com.bitinovus.verbapp.domain.model

data class ChatsResponse(
    val prompt: PromptResponse,
    val generatedResponse: GeneratedResponse,
)
