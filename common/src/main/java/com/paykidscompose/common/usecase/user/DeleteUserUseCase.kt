package com.paykidscompose.common.usecase.user

import com.paykidscompose.common.repositories.UserRepository
import com.paykidscompose.common.result.DataResourceResult

class DeleteUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): DataResourceResult<String> = userRepository.deleteUser()
}