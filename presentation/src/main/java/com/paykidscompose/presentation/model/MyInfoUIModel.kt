package com.paykidscompose.presentation.model

import com.paykidscompose.presentation.base.UIState

data class MyInfoUIModel(
    val nickname: String,
    val email: String,
    val image: Int
): UIModel()

data class MyInfoUIState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val myInfo: MyInfoUIModel? = null,
    val showPopupDialog: Boolean = false
): UIState()