package com.bitinovus.verbapp.data.remote.repository

import android.util.Log
import com.bitinovus.verbapp.data.remote.api.ApiService
import com.bitinovus.verbapp.data.remote.model.TextPrompt
import com.bitinovus.verbapp.data.remote.model.toResponse
import com.bitinovus.verbapp.domain.model.Response
import com.bitinovus.verbapp.domain.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val api: ApiService,
) : ApiRepository {
    override suspend fun prompt(token: String, textPrompt: TextPrompt): Response =
        withContext(Dispatchers.IO) {
            val response = try {
                val resp = api.chatPrompt(token = token, textPrompt = textPrompt)
                if (resp.isSuccessful) {
                    val body = resp.body()
                    body?.toResponse() ?: Response()
                } else {
                    Response()
                }
            } catch (e: Exception) {
                Log.e("ERROR-RESP", "prompt: $e")
                Response()
            }
            response
        }
}

