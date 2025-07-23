package com.paykidscompose.common.repositories

import com.paykidscompose.common.result.DataResourceResult

interface AuthRepository {
    suspend fun fetchRefreshToken(): DataResourceResult<Unit>
    suspend fun fetchLoginToken(): DataResourceResult<Boolean>
    suspend fun logout(): DataResourceResult<Unit>
}