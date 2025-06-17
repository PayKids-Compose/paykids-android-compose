package com.paykidscompose.presentation.exception

sealed class AppException(
    open val code: Int,
    val type: ExceptionType,
    override val message: String
): Throwable(message){

    data class SnackBarException(
        override val code: Int,
        override val message: String
    ) : AppException(code, ExceptionType.Snack, message)

    // 나중에 확장 가능
    // data class ToastException(...) : AppException(...)
}