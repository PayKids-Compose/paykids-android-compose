package com.paykidscompose.presentation.model.quest

import com.paykidscompose.presentation.model.UIModel

data class QuestUIModel(
    val name: String,
    val isComplete: Boolean,
    val count: Int,
    val maxCount: Int
): UIModel()
