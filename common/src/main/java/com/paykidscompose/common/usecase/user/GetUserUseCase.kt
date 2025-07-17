package com.paykidscompose.common.usecase.user

import com.paykidscompose.common.model.user.UserModel
import com.paykidscompose.common.repositories.UserRepository
import com.paykidscompose.common.result.DataResourceResult
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<DataResourceResult<UserModel>> = userRepository.getUser()
}