package com.paykidscompose.common.model.user

import com.paykidscompose.common.model.Model

data class UserModel(
    val nickname: String,
    val email: String,
    val profileImageUrl: String
): Model()
