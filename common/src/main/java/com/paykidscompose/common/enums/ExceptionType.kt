package com.paykidscompose.common.enums

sealed interface ExceptionType {
    object Snack: ExceptionType

    object Toast: ExceptionType
}