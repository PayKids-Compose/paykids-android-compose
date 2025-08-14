package com.paykidscompose.presentation.model.study

import com.paykidscompose.presentation.model.UIModel

data class ChatMessageUIModel(
    val id: String,
    val text: String,
    val isFromGpt: Boolean
): UIModel()