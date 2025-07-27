package com.paykidscompose.common.model

import com.paykidscompose.common.enums.ActionType

data class Dialog(
    val title: String,
    val message: String,
    val positiveMessage: String? = null,
    val positiveAction: ActionType? = null,
    val positiveObject: Any? = null,
    val negativeMessage: String? = null,
    val negativeAction: ActionType? = null,
    val negativeObject: Any? = null
)