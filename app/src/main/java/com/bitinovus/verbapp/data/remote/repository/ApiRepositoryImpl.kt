package com.bitinovus.verbapp.data.remote.repository

import android.util.Log
import com.bitinovus.verbapp.data.remote.api.ApiService
import com.bitinovus.verbapp.data.remote.model.TextPromptDto
import com.bitinovus.verbapp.data.remote.model.toPromptResponse
import com.bitinovus.verbapp.domain.model.PromptResponse
import com.bitinovus.verbapp.domain.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val api: ApiService,
) : ApiRepository {
    override suspend fun prompt(token: String, textPromptDto: TextPromptDto): PromptResponse =
        withContext(Dispatchers.IO) {
            val generatedResponse = try {
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
            generatedResponse
        }


}



