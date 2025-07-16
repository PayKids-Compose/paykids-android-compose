package com.paykidscompose.data.mapper.user

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.user.UserModel
import com.paykidscompose.data.model.user.UserDTO

object UserMapper : ModelMapper<UserModel, UserDTO> {
    override fun mapToModel(layerModel: UserDTO): UserModel {
        return UserModel(
            nickname = layerModel.nickname,
            email = layerModel.email,
            profileImageUrl = layerModel.profileImageURL
        )
    }

    override fun mapToLayerModel(model: UserModel): UserDTO {
        throw UnsupportedOperationException("UserModel -> UserDTO 변환은 지원하지 않습니다.")
    }
}