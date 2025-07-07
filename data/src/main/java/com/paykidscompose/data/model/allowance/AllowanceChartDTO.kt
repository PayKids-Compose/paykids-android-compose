package com.paykidscompose.data.model.allowance

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllowanceChartDTO(
    @Json(name = "id")
    val id: Long,

    @Json(name = "date")
    val date: String,

    @Json(name = "allowanceType")
    val allowanceType: String,

    @Json(name = "category")
    val category: String,

    @Json(name = "amount")
    val amount: Int,

    @Json(name = "memo")
    val memo: String
)
