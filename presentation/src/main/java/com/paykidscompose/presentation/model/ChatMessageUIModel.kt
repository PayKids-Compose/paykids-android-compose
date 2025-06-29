package com.paykidscompose.presentation.model

data class ChatMessageUIModel(
    val text: String,
    val isFromGpt: Boolean
): UIModel()