package com.paykidscompose.presentation.mapper.quiz

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.QuizModel
import com.paykidscompose.presentation.model.QuizUIModel
import com.paykidscompose.presentation.model.type.QuizType

object QuizUIModelMapper: ModelMapper<QuizModel, QuizUIModel> {
    override fun mapToModel(layerModel: QuizUIModel): QuizModel {
        throw UnsupportedOperationException("QuizUIModel -> QuizModel 변환은 지원하지 않습니다.")
    }

    override fun mapToLayerModel(model: QuizModel): QuizUIModel {
        return QuizUIModel(
            stage = model.stage,
            number = model.number,
            quizType = QuizType.valueOf(model.quizType),
            question = model.question,
            choices = model.choices.toSortedMap().values.toList(),
            answer = model.answer,
            imageUrl = model.choices.toSortedMap().values.toList(),
            totalCount = model.count
        )
    }
}