package com.bitinovus.verbapp.domain.repository

import com.bitinovus.verbapp.data.remote.model.TextPromptDto
import com.bitinovus.verbapp.domain.model.GeneratedResponse
import com.bitinovus.verbapp.domain.model.PromptResponse

interface ApiRepository {
    suspend fun prompt(token: String, textPromptDto: TextPromptDto): PromptResponse
    suspend fun aiGeneratedText(token: String, textPromptDto: TextPromptDto): GeneratedResponse
}