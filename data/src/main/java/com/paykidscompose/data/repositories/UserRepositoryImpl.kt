package com.paykidscompose.data.repositories

import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.model.user.UserDTO
import com.paykidscompose.data.network.NetworkModule
import com.paykidscompose.data.network.service.UserApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val userApiService: UserApiService = NetworkModule.provideUserApiService()) {

    suspend fun deleteUser(): DataResourceResult<String> {
        return runCatching {
            userApiService.deleteUser()
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun saveNickname(nickname: String): DataResourceResult<String> {
        return runCatching {
            userApiService.saveNickname(nickname)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun replaceNickname(newNickname: String): DataResourceResult<String> {
        return runCatching {
            userApiService.replaceNickname(newNickname)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    suspend fun replaceProfileImage(file: String): DataResourceResult<String> {
        return runCatching {
            userApiService.replaceProfileImage(file)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = { DataResourceResult.Failure(it) }
        )
    }

    fun getUser(): Flow<DataResourceResult<UserDTO>> = flow {
        emit(DataResourceResult.Loading)
        runCatching {
            userApiService.getUser()
        }.fold(
            onSuccess = { emit(DataResourceResult.Success(it.data)) },
            onFailure = { emit(DataResourceResult.Failure(it)) }
        )
    }
}