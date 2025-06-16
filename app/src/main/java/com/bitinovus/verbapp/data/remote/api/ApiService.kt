package com.bitinovus.verbapp.data.remote.api

import com.bitinovus.verbapp.data.remote.model.ChatsDto
import com.bitinovus.verbapp.data.remote.model.GeneratedTextDto
import com.bitinovus.verbapp.data.remote.model.ReplyTextDto
import com.bitinovus.verbapp.data.remote.model.TextPromptDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("chat/prompt")
    suspend fun chatPrompt(
        @Header("Authorization") token: String,
        @Body textPromptDto: TextPromptDto,
    ): Response<TextPromptDto>

    // reply to generated text
    // use the conversation_id (generated text) and the user text in the body
    @POST("chat/reply")
    suspend fun chatReplyToGenerated(
        @Header("Authorization") token: String,
        @Body textPromptDto: TextPromptDto,
    ): Response<ReplyTextDto>

    @GET("chat/ai/resp")
    suspend fun aiResponse(
        @Header("Authorization") token: String,
        @Query("conversation_id") conversationID: String,
        @Query("action_type") actionType: String,
    ): Response<GeneratedTextDto>

    @GET("chats")
    suspend fun getChats(@Header("Authorization") token: String): Response<List<ChatsDto>>
}