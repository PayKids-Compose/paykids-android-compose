package com.paykidscompose.presentation.mapper.quest

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.quest.QuestModel
import com.paykidscompose.presentation.model.quest.QuestUIModel

object QuestUIModelMapper : ModelMapper<QuestModel, QuestUIModel> {
    override fun mapToModel(layerModel: QuestUIModel): QuestModel {
        throw UnsupportedOperationException("QuestUIModel -> QuestModel 변환은 지원하지 않습니다.")
    }

    override fun mapToLayerModel(model: QuestModel): QuestUIModel {
        return QuestUIModel(
            name = model.name,
            isComplete = model.isComplete,
            count = model.count,
            maxCount = model.maxCount
        )
    }
}