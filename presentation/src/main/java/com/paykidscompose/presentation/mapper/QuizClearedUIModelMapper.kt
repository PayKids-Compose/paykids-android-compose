package com.paykidscompose.presentation.mapper

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.QuizClearedModel
import com.paykidscompose.presentation.model.QuizClearedUIModel

object QuizClearedUIModelMapper: ModelMapper<QuizClearedModel, QuizClearedUIModel> {
    override fun mapToModel(layerModel: QuizClearedUIModel): QuizClearedModel {
        return QuizClearedModel(
            message = layerModel.message,
            isCleared = layerModel.isCleared
        )
    }

    override fun mapToLayerModel(model: QuizClearedModel): QuizClearedUIModel {
        return QuizClearedUIModel(
            message = model.message,
            isCleared = model.isCleared
        )
    }

}