package com.paykidscompose.presentation.base

sealed class UIEvent {
    data class SuccessShowToast(val message: String): UIEvent()
}