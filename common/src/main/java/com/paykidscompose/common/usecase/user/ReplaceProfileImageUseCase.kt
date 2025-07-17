package com.paykidscompose.common.usecase.user

import com.paykidscompose.common.repositories.UserRepository
import com.paykidscompose.common.result.DataResourceResult

class ReplaceProfileImageUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(file: String): DataResourceResult<String> =
        userRepository.replaceProfileImage(file)
}