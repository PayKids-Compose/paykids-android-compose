package com.paykidscompose.data.mapper.quiz

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.QuizModel
import com.paykidscompose.data.model.quiz.QuizDTO

object QuizMapper : ModelMapper<QuizModel, QuizDTO> {
    override fun mapToModel(layerModel: QuizDTO): QuizModel {
        return QuizModel(
            id = layerModel.id,
            stage = layerModel.stage,
            number = layerModel.number,
            count = layerModel.count,
            quizType = layerModel.quizType,
            question = layerModel.question,
            choices = layerModel.choices,
            answer = layerModel.answer,
            imageURL = layerModel.imageURL
        )
    }

    override fun mapToLayerModel(model: QuizModel): QuizDTO {
        return QuizDTO(
            id = model.id,
            stage = model.stage,
            number = model.number,
            count = model.count,
            quizType = model.quizType,
            question = model.question,
            choices = model.choices,
            answer = model.answer,
            imageURL = model.imageURL
        )
    }

}