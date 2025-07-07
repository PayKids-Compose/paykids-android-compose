package com.paykidscompose.data.model.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginDTO(
    @Json(name = "accessToken")
    val accessToken: String,

    @Json(name = "refreshToken")
    val refreshToken: String,

    @Json(name = "tokenType")
    val tokenType: String,

    @Json(name = "isRegistered")
    val isRegistered: Boolean
)
