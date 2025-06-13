package com.paykidscompose.presentation.dummy

import androidx.annotation.DrawableRes

data class User(
    val id:	Int,
    val username: String,
    val nickname: String,
    val email: String,
    @DrawableRes
    val profileImageURL: Int = -1,
    val stageStatus: Int
)