package com.paykidscompose.presentation.base

abstract class UIState(
    open val isLoading: Boolean = false,
    open val error: String? = null
)