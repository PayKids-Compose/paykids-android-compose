package com.paykidscompose.common.exception

import com.paykidscompose.common.enums.ExceptionType
import com.paykidscompose.common.model.Dialog

sealed class PayKidsException(
    open val code: Int,
    val type: ExceptionType,
    override val message: String?
) : Throwable(message) {

    data class SnackBarException(
        override val code: Int = -1,
        override val message: String
    ) : PayKidsException(code, ExceptionType.Snack, message)

    data class ToastException(
        override val code: Int,
        override val message: String
    ) : PayKidsException(code, ExceptionType.Toast, message)

    data class DialogException(
        override val code: Int,
        val dialog: Dialog
    ) : PayKidsException(code, ExceptionType.Dialog, null)
}