package com.bitinovus.verbapp.domain.repository

import com.bitinovus.verbapp.domain.model.Response

interface ApiRepository {
    suspend fun prompt(token: String, textPrompt: String): Response
}