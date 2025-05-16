package com.bitinovus.verbapp.data.remote.api

import com.bitinovus.verbapp.data.remote.model.GeneratedText
import com.bitinovus.verbapp.data.remote.model.TextPrompt
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("chat")
    suspend fun chatPrompt(
        @Header("Authorization") token: String,
        @Body textPrompt: TextPrompt,
    ): Response<GeneratedText>
}