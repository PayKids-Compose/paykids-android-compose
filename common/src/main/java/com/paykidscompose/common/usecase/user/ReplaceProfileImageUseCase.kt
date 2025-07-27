package com.paykidscompose.common.usecase.user

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.repositories.UserRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class ReplaceProfileImageUseCase(
    private val userRepository: UserRepository
) : SuspendUseCase<ReplaceProfileImageUseCase.Params, DataResourceResult<String>>() {

    override suspend fun execute(params: Params?): DataResourceResult<String> {
        return if (params != null) {
            userRepository.replaceProfileImage(params.file)
        } else {
            DataResourceResult.Failure(PayKidsException.ToastException(code = -1,"유효한 이미지 파일이 아닙니다."))
        }
    }

    data class Params(
        val file: String
    )
}