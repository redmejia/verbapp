package com.bitinovus.verbapp.domain.repository

import com.bitinovus.verbapp.data.remote.model.TextPromptDto
import com.bitinovus.verbapp.domain.model.ChatsResponse
import com.bitinovus.verbapp.domain.model.GeneratedResponse
import com.bitinovus.verbapp.domain.model.PromptResponse
import com.bitinovus.verbapp.domain.model.ReplyResponse

interface ApiRepository {
    suspend fun prompt(token: String, textPromptDto: TextPromptDto): PromptResponse
    suspend fun replyToGeneratedText(token: String, textPromptDto: TextPromptDto): ReplyResponse
    suspend fun aiGeneratedText(token: String, textPromptDto: TextPromptDto): GeneratedResponse
    suspend fun aiResponseToReply(token: String, conversationID: String): GeneratedResponse
    suspend fun getAllChats(token: String): List<ChatsResponse>
}