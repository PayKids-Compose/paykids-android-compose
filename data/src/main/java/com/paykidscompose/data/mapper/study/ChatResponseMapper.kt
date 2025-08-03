package com.paykidscompose.data.mapper.study

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.study.ChatResponseModel
import com.paykidscompose.data.model.study.ChatResponseDTO

object ChatResponseMapper : ModelMapper<ChatResponseModel, ChatResponseDTO> {
    override fun mapToModel(layerModel: ChatResponseDTO): ChatResponseModel {
        return ChatResponseModel(
            response = layerModel.response
        )
    }

    override fun mapToLayerModel(model: ChatResponseModel): ChatResponseDTO {
        return ChatResponseDTO(
            response = model.response
        )
    }

}