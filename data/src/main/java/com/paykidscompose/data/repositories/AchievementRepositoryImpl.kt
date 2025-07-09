package com.paykidscompose.data.repositories

import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.model.BaseResponse
import com.paykidscompose.data.model.quest_achivement.AchievementDTO
import com.paykidscompose.data.network.service.AchievementApiService
import com.paykidscompose.data.network.NetworkModule

class AchievementRepositoryImpl(private val achievementApiService: AchievementApiService = NetworkModule.provideAchievementApiService()) {
    suspend fun getAchievements(): DataResourceResult<BaseResponse<List<AchievementDTO>>> {
        return runCatching { achievementApiService.getAchievements() }
            .getOrElse { return DataResourceResult.Failure(it) }
            .let { DataResourceResult.Success(it) }
    }
}