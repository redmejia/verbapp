package com.bitinovus.verbapp.domain.model

data class Response(
    val userID: String,
    val timestamp: Long,
    val text: String,
    val metadata: ResponseMetadata,
)

data class ResponseMetadata(
    val modelName: String,
)