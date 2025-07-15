package com.paykidscompose.common.model.allowance

import com.paykidscompose.common.model.AllowanceType

data class CategoryModel(
    val id: Long,
    val title: String,
    val type: AllowanceType
)