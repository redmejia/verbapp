package com.bitinovus.verbapp.domain.model

data class Response(
    val userID: String = "",
    val timestamp: Long = 0L,
    val text: String = "",
    val metadata: ResponseMetadata = ResponseMetadata(modelName = "v0"),
)

data class ResponseMetadata(
    val modelName: String,
)