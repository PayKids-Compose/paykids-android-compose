package com.paykidscompose.common.model

import kotlinx.coroutines.flow.Flow

/**
 * 로그인 기록과 회원 여부를 판단하는 매니저
 */
interface AuthStatusManager {
    fun getIsRegistered() : Flow<Boolean>
    fun getToken() : Flow<String>
}