package com.bitinovus.verbapp.data.remote.repository

import android.util.Log
import com.bitinovus.verbapp.data.remote.api.ApiService
import com.bitinovus.verbapp.data.remote.model.TextPromptDto
import com.bitinovus.verbapp.data.remote.model.toChatResponseList
import com.bitinovus.verbapp.data.remote.model.toGeneratedResponse
import com.bitinovus.verbapp.data.remote.model.toPromptResponse
import com.bitinovus.verbapp.data.remote.model.toReplyResponse
import com.bitinovus.verbapp.domain.model.ChatsResponse
import com.bitinovus.verbapp.domain.model.GeneratedResponse
import com.bitinovus.verbapp.domain.model.PromptResponse
import com.bitinovus.verbapp.domain.model.ReplyResponse
import com.bitinovus.verbapp.domain.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val api: ApiService,
) : ApiRepository {
    override suspend fun prompt(token: String, textPromptDto: TextPromptDto): PromptResponse =
        withContext(Dispatchers.IO) {
            val prompt = try {
                val resp = api.chatPrompt(token = token, textPromptDto = textPromptDto)
                if (resp.isSuccessful) {
                    val body = resp.body()
                    body?.toPromptResponse() ?: PromptResponse()
                } else {
                    PromptResponse()
                }
            } catch (_: Exception) {
                PromptResponse()
            }
            prompt
        }

    override suspend fun replyToGeneratedText(
        token: String,
        textPromptDto: TextPromptDto,
    ): ReplyResponse = withContext(Dispatchers.IO) {
        val replyPrompt = try {
            val resp = api.chatReplyToGenerated(token = token, textPromptDto = textPromptDto)
            if (resp.isSuccessful) {
                val body = resp.body()
                Log.d("CHAT", "BODY : $body")
                body?.toReplyResponse() ?: ReplyResponse()
            } else {
                ReplyResponse()
            }
        } catch (_: Exception) {
            ReplyResponse()
        }
        replyPrompt
    }

    override suspend fun aiGeneratedText(
        token: String,
        textPromptDto: TextPromptDto,
    ): GeneratedResponse =
        withContext(Dispatchers.IO) {
            val generatedResponse = try {
                val resp = api.aiResponse(token = token, textPromptDto = textPromptDto)
                if (resp.isSuccessful) {
                    val body = resp.body()
                    body?.toGeneratedResponse() ?: GeneratedResponse()
                } else {
                    GeneratedResponse()
                }
            } catch (_: Exception) {
                GeneratedResponse()
            }
            generatedResponse
        }

    override suspend fun aiResponseToReply(
        token: String,
        conversationID: String,
    ): GeneratedResponse =
        withContext(Dispatchers.IO) {
            val generatedResponse = try {
                val resp = api.aiResponseReply(token = token, conversationID = conversationID)
                if (resp.isSuccessful) {
                    val body = resp.body()
                    body?.toGeneratedResponse() ?: GeneratedResponse()
                } else {
                    GeneratedResponse()
                }
            } catch (_: Exception) {
                GeneratedResponse()
            }
            generatedResponse
        }

    override suspend fun getAllChats(token: String): List<ChatsResponse> =
        withContext(Dispatchers.IO) {
            val chats = try {
                val resp = api.getChats(token = token)
                if (resp.isSuccessful) {
                    val body = resp.body()
                    body?.toChatResponseList() ?: emptyList<ChatsResponse>()
                } else {
                    emptyList<ChatsResponse>()
                }
            } catch (_: Exception) {
                emptyList<ChatsResponse>()
            }
            chats
        }
}



