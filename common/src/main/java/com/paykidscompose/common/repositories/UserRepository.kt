package com.paykidscompose.common.repositories

import com.paykidscompose.common.model.user.UserModel
import com.paykidscompose.common.result.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun deleteUser(): DataResourceResult<String>
    suspend fun saveNickname(nickname: String): DataResourceResult<String>
    suspend fun replaceNickname(newNickname: String): DataResourceResult<String>
    suspend fun replaceProfileImage(file: String): DataResourceResult<String>
    fun getUser(): Flow<DataResourceResult<UserModel>>
}