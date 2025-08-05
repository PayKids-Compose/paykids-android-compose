package com.paykidscompose.common.repositories

import com.paykidscompose.common.model.quest.QuestModel
import com.paykidscompose.common.result.DataResourceResult

interface QuestRepository {
    suspend fun getQuests(): DataResourceResult<List<QuestModel>>
}