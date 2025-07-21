package com.paykidscompose.data.mapper.allowance

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.allowance.CategoryModel
import com.paykidscompose.data.model.allowance.CategoryDTO

object CategoryMapper : ModelMapper<CategoryModel, CategoryDTO> {
    override fun mapToModel(layerModel: CategoryDTO): CategoryModel {
        return CategoryModel(
            id = layerModel.id,
            title = layerModel.title,
            type = AllowanceType.valueOf(layerModel.allowanceType)
        )
    }

    override fun mapToLayerModel(model: CategoryModel): CategoryDTO {
        return CategoryDTO(
            id = model.id,
            title = model.title,
            allowanceType = model.type.name
        )
    }

}