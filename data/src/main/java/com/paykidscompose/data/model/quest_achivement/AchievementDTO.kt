package com.paykidscompose.data.model.quest_achivement

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AchievementDTO(
    @Json(name = "isCompleted")
    val isCompleted: Boolean,

    @Json(name = "name")
    val name: String,

    @Json(name = "description")
    val description: String,

    @Json(name = "imageURL")
    val imageURL: String
)
