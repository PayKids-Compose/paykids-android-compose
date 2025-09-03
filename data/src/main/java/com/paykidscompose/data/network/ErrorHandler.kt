package com.paykidscompose.data.network

import com.paykidscompose.common.enums.ActionType
import com.paykidscompose.common.exception.PayKidsApiException
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.Dialog
import com.paykidscompose.data.model.ErrorResponse
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import javax.inject.Inject


class ErrorHandler @Inject constructor(
    private val moshi: Moshi
) {
    fun handleException(e: Throwable): PayKidsException {
        return when (e) {
            is HttpException -> {
                val errorBody = e.response()?.errorBody()?.string()
                val adapter = moshi.adapter(ErrorResponse::class.java)
                val errorResponse = errorBody?.let { adapter.fromJson(it) }
                PayKidsException.DialogException(
                    code = e.code(),
                    dialog = Dialog(
                        title = "Http 에러가 발생했어요!",
                        message = "${e.code()}: ${errorResponse?.message ?: "유효하지 않은 에러입니다."}",
                        positiveAction = ActionType.RETRY_API,
                        positiveMessage = "재시도"
                    )
                )
            }

            is PayKidsApiException -> {
                PayKidsException.ToastException(message = e.message)
            }

            else -> {
                PayKidsException.ToastException(
                    message = "앱을 다시 시작해주세요"
                )
            }
        }
    }
}