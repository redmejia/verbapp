package com.bitinovus.verbapp.domain.repository

import com.bitinovus.verbapp.data.remote.model.TextPrompt
import com.bitinovus.verbapp.domain.model.Response

interface ApiRepository {
    suspend fun prompt(token: String, textPrompt: TextPrompt): Response
}