package com.paykidscompose.common.model

import kotlinx.coroutines.flow.Flow

abstract class TokenManager {
    abstract fun getToken() : Flow<String>
}