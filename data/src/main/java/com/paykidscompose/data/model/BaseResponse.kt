package com.paykidscompose.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @Json(name = "code")
    val code: Int,

    @Json(name = "status")
    val status: String,

    @Json(name = "message")
    val message: String,

    @Json(name = "data")
    val data: T
)