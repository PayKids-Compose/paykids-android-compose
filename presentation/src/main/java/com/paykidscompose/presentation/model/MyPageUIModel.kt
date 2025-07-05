package com.paykidscompose.presentation.model

import com.paykidscompose.presentation.base.UIState

data class MyPageUIModel(
    val nickname: String,
    val image: Int
): UIModel()

data class MyPageUIState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val myPage: MyPageUIModel? = null,
    val showLogoutDialog: Boolean = false
): UIState()