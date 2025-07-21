package com.paykidscompose.common.usecase.user

import com.paykidscompose.common.repositories.UserRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class ReplaceNicknameUseCase(
    private val userRepository: UserRepository
) : SuspendUseCase<ReplaceNicknameUseCase.Params, DataResourceResult<String>>() {

    override suspend fun execute(params: Params?): DataResourceResult<String> {
        return if (params != null && params.nickname.length < 2) {
            userRepository.replaceNickname(params.nickname)
        } else {
            DataResourceResult.Failure(IllegalArgumentException("닉네임은 2자 이상이어야 합니다."))
        }
    }

    data class Params(
        val nickname: String
    )
}