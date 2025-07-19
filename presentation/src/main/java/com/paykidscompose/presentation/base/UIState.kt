package com.paykidscompose.presentation.base

import com.paykidscompose.common.exception.PayKidsException

abstract class UIState(
    open val isLoading: Boolean = false,
    open val error: PayKidsException? = null
)