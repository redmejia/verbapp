package com.bitinovus.verbapp.data.remote.repository

import com.bitinovus.verbapp.data.remote.api.ApiService
import com.bitinovus.verbapp.domain.model.Response
import com.bitinovus.verbapp.domain.repository.ApiRepository
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val api: ApiService,
) : ApiRepository {
    override suspend fun prompt(token: String, textPrompt: String): Response {
        TODO("Not yet implemented")
    }

}