package com.bitinovus.verbapp.data.remote.api

import com.bitinovus.verbapp.data.remote.model.GeneratedTextDto
import com.bitinovus.verbapp.data.remote.model.TextPromptDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

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

    @POST("chat/ai/text")
    suspend fun aiResponse(
        @Header("Authorization") token: String,
        @Body textPromptDto: TextPromptDto, // send conversation id
    ): Response<GeneratedTextDto>
}