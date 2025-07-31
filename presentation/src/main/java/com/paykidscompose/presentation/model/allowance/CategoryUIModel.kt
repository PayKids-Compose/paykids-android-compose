package com.paykidscompose.presentation.model.allowance

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.presentation.model.UIModel

data class CategoryUIModel(
    val id: Long,
    val title: String,
    val type: AllowanceType
) : UIModel()