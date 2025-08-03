package com.paykidscompose.presentation.mapper.quiz

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.quiz.QuizClearedModel
import com.paykidscompose.presentation.model.quiz.QuizClearedUIModel

object QuizClearedUIModelMapper: ModelMapper<QuizClearedModel, QuizClearedUIModel> {
    override fun mapToModel(layerModel: QuizClearedUIModel): QuizClearedModel {
        throw UnsupportedOperationException("QuizClearedUIModel -> QuizClearedModel 변환은 지원하지 않습니다.")
    }

    override fun mapToLayerModel(model: QuizClearedModel): QuizClearedUIModel {
        return QuizClearedUIModel(
            message = model.message,
            isCleared = model.isCleared
        )
    }

}