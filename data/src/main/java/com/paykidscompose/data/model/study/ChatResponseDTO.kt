package com.paykidscompose.data.model.study

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatResponseDTO(
    val response: String
)
