package com.paykidscompose.presentation.dummy

import androidx.annotation.DrawableRes
import com.paykidscompose.presentation.R

data class User(
    val id: String,
    val username: String,
    var nickname: String,
    var email: String = "kkks@naver.com",
    @DrawableRes
    val profileImageURL: Int =  R.drawable.img_default_profile,
    val stageStatus: Int = 1
)

object DummyUser {

    private val userList = mutableListOf<User>()
    var id = 1

    fun getUser(id: String): User? {
        return userList.find { it.id == id}
    }

    fun setUser(user: User) {
        val index = userList.indexOfFirst { it.id == user.id }
        if (index != -1) {
            userList[index] = user
        } else {
            userList.add(user)
        }
    }

    fun createDummyUser() {
        val user = User(
            id = "TestMingyu",
            username = "Mingyu",
            nickname = "민규당",
            email = "test@naver.com"
        )
        setUser(user)
    }

    fun getUsers(): List<User> {
        return userList
    }
}