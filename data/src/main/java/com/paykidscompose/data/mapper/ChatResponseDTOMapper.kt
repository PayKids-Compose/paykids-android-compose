package com.paykidscompose.data.mapper

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.ChatResponseModel
import com.paykidscompose.data.model.study.ChatResponseDTO

object ChatResponseDTOMapper : ModelMapper<ChatResponseModel, ChatResponseDTO> {
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