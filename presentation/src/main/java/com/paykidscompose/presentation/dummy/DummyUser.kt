package com.paykidscompose.presentation.dummy

import androidx.annotation.DrawableRes

data class User(
    val id:	String,
    val username: String,
    val nickname: String,
    val email: String,
    @DrawableRes
    val profileImageURL: Int = -1,
    val stageStatus: Int = 1
)

object DummyUser {
    fun getUser(): User =
            User(
                id = "boo",
                username = "mingyu",
                nickname = "개발꿈나무",
                email = "mingyuson@example.com",
                profileImageURL = com.google.android.material.R.drawable.abc_ic_star_black_16dp, // 예시 이미지
                stageStatus = 1
            )
}