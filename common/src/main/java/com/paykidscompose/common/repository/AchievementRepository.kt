package com.paykidscompose.common.repository

import com.paykidscompose.common.model.achievement.AchievementModel
import com.paykidscompose.common.result.DataResourceResult

interface AchievementRepository {
    suspend fun getAchievements(): DataResourceResult<List<AchievementModel>>
}