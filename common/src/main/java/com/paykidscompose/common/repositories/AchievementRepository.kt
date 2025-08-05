package com.paykidscompose.common.repositories

import com.paykidscompose.common.model.achievement.AchievementModel
import com.paykidscompose.common.result.DataResourceResult

interface AchievementRepository {
    suspend fun getAchievements(): DataResourceResult<List<AchievementModel>>
}