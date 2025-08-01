package com.paykidscompose.data.mapper.allowance

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.allowance.AllowanceChartCategoryModel
import com.paykidscompose.data.model.allowance.AllowanceChartCategoryDTO

object AllowanceChartCategoryMapper : ModelMapper<AllowanceChartCategoryModel, AllowanceChartCategoryDTO> {
    override fun mapToModel(layerModel: AllowanceChartCategoryDTO): AllowanceChartCategoryModel {
        return AllowanceChartCategoryModel(
            type = AllowanceType.valueOf(layerModel.allowanceType),
            category = layerModel.category,
            percent = layerModel.percent?.toInt() ?: 0,
            amount = layerModel.amount
        )
    }

    override fun mapToLayerModel(model: AllowanceChartCategoryModel): AllowanceChartCategoryDTO {
        return AllowanceChartCategoryDTO(
            allowanceType = model.type.name,
            category = model.category,
            percent = model.percent.toString(),
            amount = model.amount
        )
    }

}