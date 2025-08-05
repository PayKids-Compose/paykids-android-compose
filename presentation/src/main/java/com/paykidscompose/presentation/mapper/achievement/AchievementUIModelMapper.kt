package com.paykidscompose.presentation.mapper.achievement

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.achievement.AchievementModel
import com.paykidscompose.presentation.model.achievement.AchievementUIModel

object AchievementUIModelMapper : ModelMapper<AchievementModel, AchievementUIModel> {
    override fun mapToModel(layerModel: AchievementUIModel): AchievementModel {
        throw UnsupportedOperationException("AchievementUIModel -> AchievementModel 변환은 지원하지 않습니다.")
    }

    override fun mapToLayerModel(model: AchievementModel): AchievementUIModel {
        return AchievementUIModel(
            isCompleted = model.isCompleted,
            name = model.name,
            description = model.description,
            imageURL = model.imageURL
        )
    }
}