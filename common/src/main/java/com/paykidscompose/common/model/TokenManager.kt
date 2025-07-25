package com.paykidscompose.common.model

import kotlinx.coroutines.flow.Flow

abstract class TokenManager {
    abstract fun getIsRegistered() : Flow<Boolean>
    abstract fun getToken() : Flow<String>
}