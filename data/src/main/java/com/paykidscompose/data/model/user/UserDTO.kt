package com.paykidscompose.data.model.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDTO(
    @Json(name = "id")
    val id: Long,

    @Json(name = "sub")
    val sub: String,

    @Json(name = "username")
    val username: String,

    @Json(name = "uuid")
    val uuid: String,

    @Json(name = "nickname")
    val nickname: String,

    @Json(name = "email")
    val email: String,

    @Json(name = "profileImageURL")
    val profileImageURL: String,

    @Json(name = "stageStatus")
    val stageStatus: Int
)