package com.paykidscompose.common.model.allowance

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.model.Model

data class CategoryModel(
    val id: Long,
    val title: String,
    val type: AllowanceType
) : Model()