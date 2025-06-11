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
//    @POST("chat")
//    suspend fun chatPrompt(
//        @Header("Authorization") token: String,
//        @Body textPrompt: TextPrompt,
//    ): Response<GeneratedText>

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

    @GET("chat/reply")
    suspend fun aiResponseReply(
        @Header("Authorization") token: String,
        @Query("conversation_id") conversationID: String,
    ): Response<GeneratedTextDto>

    @POST("chat/ai/text")
    suspend fun aiResponse(
        @Header("Authorization") token: String,
        @Body textPromptDto: TextPromptDto, // send conversation id
    ): Response<GeneratedTextDto>

    @GET("chat")
    suspend fun getChats(@Header("Authorization") token: String): Response<List<ChatsDto>>
}