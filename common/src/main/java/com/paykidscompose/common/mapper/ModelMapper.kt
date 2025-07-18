package com.paykidscompose.common.mapper

import com.paykidscompose.common.model.Model

/**
 * M: 공통 모델을 의미합니다. common의 모델 (UserModel)
 *
 * LM: 다른 레이어의 모델을 의미합니다. (UserDto or UserUIModel)
 *
 * 인터페이스를 구현할 때 사용 안 하는 함수는 비워두지 말고 예외를 던져주세요.
 */
interface ModelMapper<M : Model, LM> {
    fun mapToModel(layerModel: LM): M
    fun mapToLayerModel(model: M): LM
}