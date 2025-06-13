package com.paykidscompose.presentation.dummy

import androidx.annotation.DrawableRes
import com.paykidscompose.presentation.R

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
                id = "bosshee23",
                username = "mingyu",
                nickname = "개발꿈나무",
                email = "mingyuson@example.com",
                profileImageURL = R.drawable.img_default_profile, // 예시 이미지
                stageStatus = 1
            )
}