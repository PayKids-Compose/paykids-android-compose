package com.paykidscompose.common.repositories

import com.paykidscompose.common.result.DataResourceResult

interface AuthRepository {
    suspend fun fetchRefreshToken(): DataResourceResult<Unit>
    suspend fun fetchLoginToken(): DataResourceResult<Unit>
    suspend fun logout(): DataResourceResult<Unit>
}