package com.paykidscompose.data.repository

import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.common.model.achievement.AchievementModel
import com.paykidscompose.common.repositories.AchievementRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.data.mapper.achievement.AchievementMapper
import com.paykidscompose.data.network.service.AchievementApiService

class AchievementRepositoryImpl(private val achievementApiService: AchievementApiService) :
    AchievementRepository {
    override suspend fun getAchievements(): DataResourceResult<List<AchievementModel>> {
        return runCatching {
            achievementApiService.getAchievements()
        }.fold(
            onSuccess = {
                DataResourceResult.Success(
                    it.data.map { achievementDTO ->
                        AchievementMapper.mapToModel(achievementDTO)
                    }
                )
            },
            onFailure = {
                DataResourceResult.Failure(
                    PayKidsException.ToastException(
                        code = -1,
                        message = it.message ?: ""
                    )
                )
            }
        )
    }
}