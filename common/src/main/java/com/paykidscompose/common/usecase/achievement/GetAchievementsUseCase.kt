package com.paykidscompose.common.usecase.achievement

import com.paykidscompose.common.model.achievement.AchievementModel
import com.paykidscompose.common.repositories.AchievementRepository
import com.paykidscompose.common.result.DataResourceResult
import com.paykidscompose.common.usecase.base.SuspendUseCase

class GetAchievementsUseCase(
    private val achievementRepository: AchievementRepository
) : SuspendUseCase<Unit, DataResourceResult<List<AchievementModel>>>() {
    override suspend fun execute(params: Unit?): DataResourceResult<List<AchievementModel>> {
        return achievementRepository.getAchievements()
    }
}