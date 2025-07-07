package com.paykidscompose.data.model.allowance

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllowanceChartAmountDTO(
    @Json(name = "date")
    val date: String,  // ISO date string (e.g., "2025-07-07")

    @Json(name = "amount")
    val amount: Int,

    @Json(name = "allowanceType")
    val allowanceType: String
)
