package com.paykidscompose.data.model.quest_achivement

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestDTO(
    @Json(name = "name")
    val name: String,

    @Json(name = "isComplete")
    val isComplete: Boolean,

    @Json(name = "count")
    val count: Int,

    @Json(name = "maxCount")
    val maxCount: Int
)
