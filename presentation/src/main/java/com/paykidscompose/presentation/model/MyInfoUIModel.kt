package com.paykidscompose.presentation.model

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.user.UserModel

data class MyInfoUIModel(
    val nickname: String,
    val email: String,
    val image: String
) : UIModel()

object MyInfoUIModelMapper : ModelMapper<UserModel, MyInfoUIModel> {
    override fun mapToModel(layerModel: MyInfoUIModel): UserModel {
        throw UnsupportedOperationException("MyInfoUIModel -> UserModel 변환은 지원하지 않습니다.")
    }

    override fun mapToLayerModel(model: UserModel): MyInfoUIModel {
        return MyInfoUIModel(
            nickname = model.nickname,
            email = model.email,
            image = model.profileImageUrl
        )
    }
}