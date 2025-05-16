package com.bitinovus.verbapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class TextPrompt(
    @SerializedName("text") val text: String = "",
)