package com.paykidscompose.common.model.achievement

import com.paykidscompose.common.model.Model

data class AchievementModel(
    val isCompleted: Boolean,
    val name: String,
    val description: String,
    val imageURL: String
): Model()
