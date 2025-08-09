package com.paykidscompose.data.repository

import androidx.core.content.edit
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.user.UserModel
import com.paykidscompose.common.repositories.UserRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.database.PayKidsPreference
import com.paykidscompose.data.mapper.user.UserMapper
import com.paykidscompose.data.model.AuthStatusManagerImpl
import com.paykidscompose.data.network.service.UserApiService
import com.paykidscompose.data.util.ACCESS_TOKEN
import com.paykidscompose.data.util.REFRESH_TOKEN
import com.paykidscompose.data.util.USER_REGISTERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val userApiService: UserApiService
) : UserRepository {

    override suspend fun deleteUser(): DataResourceResult<String> {
        return runCatching {
            userApiService.deleteUser()
        }.fold(
            onSuccess = {
                PayKidsPreference.getInstance().edit {
                    remove(ACCESS_TOKEN)
                    remove(REFRESH_TOKEN)
                    remove(USER_REGISTERED)
                }
                AuthStatusManagerImpl.notifyLoginScreenNav()
                DataResourceResult.Success(it.data)
            },
            onFailure = {
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = it.message ?: "회원탈퇴 과정 중 통신 에러 발생"
                    )
                )
            }
        )
    }

    override suspend fun saveNickname(nickname: String): DataResourceResult<String> {
        return runCatching {
            userApiService.saveNickname(nickname)
        }.fold(
            onSuccess = {
                PayKidsPreference.getInstance().edit {
                    putBoolean(USER_REGISTERED, true)
                }
                DataResourceResult.Success(it.data)
            },
            onFailure = {
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = it.message ?: "닉네임 저장 과정 중 통신 에러 발생"
                    )
                )
            }
        )
    }

    override suspend fun replaceNickname(newNickname: String): DataResourceResult<String> {
        return runCatching {
            userApiService.replaceNickname(newNickname)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = {
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = it.message ?: "닉네임 변경 과정 중 통신 에러 발생"
                    )
                )
            }
        )
    }

    override suspend fun replaceProfileImage(file: String): DataResourceResult<String> {
        return runCatching {
            userApiService.replaceProfileImage(file)
        }.fold(
            onSuccess = { DataResourceResult.Success(it.data) },
            onFailure = {
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = it.message ?: "프로필 변경 과정 중 통신 에러 발생"
                    )
                )
            }
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
            onFailure = {
                emit(
                    DataResourceResult.Failure(
                        PayKidsException.ToastException(
                            code = -1,
                            message = it.message ?: "유저 로드 과정 중 통신 에러 발생"
                        )
                    )
                )
            }
        )
    }
}