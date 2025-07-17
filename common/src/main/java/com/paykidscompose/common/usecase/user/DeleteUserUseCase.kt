package com.paykidscompose.common.usecase.user

import com.paykidscompose.common.repositories.UserRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class DeleteUserUseCase(
    private val userRepository: UserRepository
) : SuspendUseCase<Unit, DataResourceResult<String>>() {
    override suspend fun execute(params: Unit?): DataResourceResult<String> = userRepository.deleteUser()
}