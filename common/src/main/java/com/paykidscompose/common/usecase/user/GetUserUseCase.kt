package com.paykidscompose.common.usecase.user

import com.paykidscompose.common.model.user.UserModel
import com.paykidscompose.common.repository.UserRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(
    private val userRepository: UserRepository
) : FlowUseCase<Unit, DataResourceResult<UserModel>>() {
    override fun execute(params: Unit?): Flow<DataResourceResult<UserModel>> = userRepository.getUser()
}