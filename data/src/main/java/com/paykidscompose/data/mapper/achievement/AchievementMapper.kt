package com.paykidscompose.data.mapper.achievement

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.achievement.AchievementModel
import com.paykidscompose.data.model.achievement.AchievementDTO

object AchievementMapper : ModelMapper<AchievementModel, AchievementDTO> {
    override fun mapToModel(layerModel: AchievementDTO): AchievementModel {
        return AchievementModel(
            isCompleted = layerModel.isCompleted,
            name = layerModel.name,
            description = layerModel.description,
            imageURL = layerModel.imageURL
        )
    }

    override fun mapToLayerModel(model: AchievementModel): AchievementDTO {
        throw UnsupportedOperationException("AchievementModel -> AchievementDTO 변환은 지원하지 않습니다.")
    }


}