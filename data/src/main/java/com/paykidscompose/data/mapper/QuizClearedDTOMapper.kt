package com.paykidscompose.data.mapper

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.QuizClearedModel
import com.paykidscompose.data.model.quiz.QuizClearedDTO

object QuizClearedDTOMapper : ModelMapper<QuizClearedModel, QuizClearedDTO> {
    override fun mapToModel(layerModel: QuizClearedDTO): QuizClearedModel {
        return QuizClearedModel(
            message = layerModel.message,
            isCleared = layerModel.isCleared
        )
    }

    override fun mapToLayerModel(model: QuizClearedModel): QuizClearedDTO {
        return QuizClearedDTO(
            message = model.message,
            isCleared = model.isCleared
        )
    }
}