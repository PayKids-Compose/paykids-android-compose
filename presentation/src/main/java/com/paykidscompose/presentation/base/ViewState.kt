package com.paykidscompose.presentation.base

import com.paykidscompose.presentation.exception.AppException

open class ViewState(
    open val isLoading: Boolean = false,
    open val error: AppException? = null
)