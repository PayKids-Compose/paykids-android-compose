package com.paykidscompose.data.model.allowance

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllowanceChartCategoryDTO(
    @Json(name = "allowanceType")
    val allowanceType: String,

    @Json(name = "category")
    val category: String,

    @Json(name = "percent")
    val percent: String,

    @Json(name = "amount")
    val amount: Int
)
