package com.paykidscompose.presentation.model.achievement

import com.paykidscompose.presentation.model.UIModel

data class AchievementUIModel (
    val isCompleted: Boolean,
    val name: String,
    val description: String,
    val imageURL: String
): UIModel()