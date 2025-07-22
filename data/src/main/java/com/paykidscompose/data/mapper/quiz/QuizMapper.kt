package com.paykidscompose.data.mapper.quiz

import com.paykidscompose.common.enums.QuizType
import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.QuizModel
import com.paykidscompose.data.model.quiz.QuizDTO

object QuizMapper : ModelMapper<QuizModel, QuizDTO> {
    override fun mapToModel(layerModel: QuizDTO): QuizModel {
        val baseQuizType = when (layerModel.quizType) {
            QuizType.TEXT_CHOICE.name -> QuizType.TEXT_CHOICE
            QuizType.IMAGE_CHOICE.name -> QuizType.IMAGE_CHOICE
            QuizType.SHORT_ANSWER.name -> QuizType.SHORT_ANSWER
            else -> throw IllegalArgumentException("Invalid quizType: ${layerModel.quizType}")
        }

        val finalQuizType = when (baseQuizType) {
            QuizType.TEXT_CHOICE -> {
                if (layerModel.imageURL.isNotEmpty()) QuizType.TEXT_CHOICE_IMAGE else baseQuizType
            }
            QuizType.SHORT_ANSWER -> {
                if (layerModel.imageURL.isNotEmpty()) QuizType.SHORT_ANSWER_IMAGE else baseQuizType
            }
            else -> baseQuizType // IMAGE_CHOICE는 확장 없음
        }

        return QuizModel(
            id = layerModel.id,
            stage = layerModel.stage,
            number = layerModel.number,
            count = layerModel.count,
            quizType = finalQuizType,
            question = layerModel.question,
            choices = layerModel.choices,
            answer = layerModel.answer,
            imageURL = layerModel.imageURL
        )
    }

    override fun mapToLayerModel(model: QuizModel): QuizDTO {
        val baseQuizType = when (model.quizType) {
            QuizType.TEXT_CHOICE, QuizType.TEXT_CHOICE_IMAGE -> QuizType.TEXT_CHOICE.name
            QuizType.IMAGE_CHOICE -> QuizType.IMAGE_CHOICE.name
            QuizType.SHORT_ANSWER, QuizType.SHORT_ANSWER_IMAGE -> QuizType.SHORT_ANSWER.name
        }

        return QuizDTO(
            id = model.id,
            stage = model.stage,
            number = model.number,
            count = model.count,
            quizType = baseQuizType,
            question = model.question,
            choices = model.choices,
            answer = model.answer,
            imageURL = model.imageURL
        )
    }

}