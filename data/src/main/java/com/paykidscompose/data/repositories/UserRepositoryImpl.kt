package com.paykidscompose.data.repositories

import com.paykidscompose.common.model.user.UserModel
import com.paykidscompose.common.repositories.UserRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.mapper.user.UserMapper
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.UserApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userApiService: UserApiService = NetworkModule.provideUserApiService()
) : UserRepository {

    override suspend fun deleteUser(): DataResourceResult<String> {
        return runCatching {
            userApiService.deleteUser()
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    override suspend fun saveNickname(nickname: String): DataResourceResult<String> {
        return runCatching {
            userApiService.saveNickname(nickname)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    override suspend fun replaceNickname(newNickname: String): DataResourceResult<String> {
        return runCatching {
            userApiService.replaceNickname(newNickname)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    override suspend fun replaceProfileImage(file: String): DataResourceResult<String> {
        return runCatching {
            userApiService.replaceProfileImage(file)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    override fun getUser(): Flow<DataResourceResult<UserModel>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            userApiService.getUser()
        }.fold(
            onSuccess = {
                val user = UserMapper.mapToModel(it.data)
                emit(DataResourceResult.Success(user))
            },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }
}