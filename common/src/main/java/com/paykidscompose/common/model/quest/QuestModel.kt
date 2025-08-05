package com.paykidscompose.common.model.quest

import com.paykidscompose.common.model.Model

data class QuestModel(
    val name: String,
    val isComplete: Boolean,
    val count: Int,
    val maxCount: Int
): Model()