package com.paykidscompose.presentation.mapper.my

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.user.UserModel
import com.paykidscompose.presentation.model.MyPageUIModel

object MyPageUIModelMapper : ModelMapper<UserModel, MyPageUIModel> {
    override fun mapToModel(layerModel: MyPageUIModel): UserModel {
        throw UnsupportedOperationException("MyPageUIModel -> UserModel 변환은 지원하지 않습니다.")
    }

    override fun mapToLayerModel(model: UserModel): MyPageUIModel {
        return MyPageUIModel(
            nickname = model.nickname,
            image = model.profileImageUrl
        )
    }
}