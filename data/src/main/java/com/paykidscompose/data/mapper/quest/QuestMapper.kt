package com.paykidscompose.data.mapper.quest

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.quest.QuestModel
import com.paykidscompose.data.model.quest.QuestDTO

object QuestMapper : ModelMapper<QuestModel, QuestDTO> {
    override fun mapToModel(layerModel: QuestDTO): QuestModel {
        return QuestModel(
            name = layerModel.name,
            isComplete = layerModel.isComplete,
            count = layerModel.count,
            maxCount = layerModel.maxCount
        )
    }

    override fun mapToLayerModel(model: QuestModel): QuestDTO {
        throw UnsupportedOperationException("QuestModel -> QuestDTO 변환은 지원하지 않습니다.")
    }

}