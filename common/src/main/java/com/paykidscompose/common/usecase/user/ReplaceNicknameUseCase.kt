package com.paykidscompose.common.usecase.user

import com.paykidscompose.common.repositories.UserRepository
import com.paykidscompose.common.result.DataResourceResult

class ReplaceNicknameUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(nickname: String): DataResourceResult<String> {

        if (nickname.length < 2) {
            return DataResourceResult.Failure(IllegalArgumentException("닉네임은 2자 이상이어야 합니다."))
        }

        return userRepository.replaceNickname(nickname)
    }
}