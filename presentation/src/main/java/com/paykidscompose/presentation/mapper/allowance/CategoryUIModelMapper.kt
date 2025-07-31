package com.paykidscompose.presentation.mapper.allowance

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.allowance.CategoryModel
import com.paykidscompose.presentation.model.allowance.CategoryUIModel

object CategoryUIModelMapper : ModelMapper<CategoryModel, CategoryUIModel> {
    override fun mapToModel(layerModel: CategoryUIModel): CategoryModel {
        return CategoryModel(
            id = layerModel.id,
            title = layerModel.title,
            type = layerModel.type
        )
    }

    override fun mapToLayerModel(model: CategoryModel): CategoryUIModel {
        return CategoryUIModel(
            id = model.id,
            title = model.title,
            type = model.type
        )
    }
}