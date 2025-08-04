package com.paykidscompose.presentation.mapper.study

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.study.ChatResponseModel
import com.paykidscompose.presentation.model.study.ChatMessageUIModel

object ChatMessageUIModelMapper: ModelMapper<ChatResponseModel, ChatMessageUIModel> {
    override fun mapToModel(layerModel: ChatMessageUIModel): ChatResponseModel {
        throw UnsupportedOperationException("ChatMessageUIModel -> ChatResponseModel 변환은 지원하지 않습니다.")
    }

    override fun mapToLayerModel(model: ChatResponseModel): ChatMessageUIModel {
        return ChatMessageUIModel(
            text = model.response,
            isFromGpt = true
        )
    }

}