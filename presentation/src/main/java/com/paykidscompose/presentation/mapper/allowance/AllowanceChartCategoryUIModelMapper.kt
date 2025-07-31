package com.paykidscompose.presentation.mapper.allowance

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.allowance.AllowanceChartCategoryModel
import com.paykidscompose.presentation.model.allowance.AllowanceChartCategoryUIModel

object AllowanceChartCategoryUIModelMapper :
    ModelMapper<AllowanceChartCategoryModel, AllowanceChartCategoryUIModel> {
    override fun mapToModel(layerModel: AllowanceChartCategoryUIModel): AllowanceChartCategoryModel {
        return AllowanceChartCategoryModel(
            type = layerModel.type,
            category = layerModel.category,
            percent = layerModel.percent,
            amount = layerModel.amount
        )
    }

    override fun mapToLayerModel(model: AllowanceChartCategoryModel): AllowanceChartCategoryUIModel {
        return AllowanceChartCategoryUIModel(
            type = model.type,
            category = model.category,
            percent = model.percent,
            amount = model.amount
        )
    }
}