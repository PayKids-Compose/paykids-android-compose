package com.paykidscompose.common.usecase.user

import com.paykidscompose.common.repositories.UserRepository
import com.paykidscompose.common.result.DataResourceResult

class SaveNicknameUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(nickname: String): DataResourceResult<String> =
        userRepository.saveNickname(nickname)
}