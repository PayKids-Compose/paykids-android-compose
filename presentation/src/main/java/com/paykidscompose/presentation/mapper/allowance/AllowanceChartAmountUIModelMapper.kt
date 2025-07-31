package com.paykidscompose.presentation.mapper.allowance

import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.allowance.AllowanceChartAmountModel
import com.paykidscompose.presentation.model.allowance.AllowanceChartAmountUIModel

object AllowanceChartAmountUIModelMapper :
    ModelMapper<AllowanceChartAmountModel, AllowanceChartAmountUIModel> {
    override fun mapToModel(layerModel: AllowanceChartAmountUIModel): AllowanceChartAmountModel {
        return AllowanceChartAmountModel(
            date = layerModel.date,
            amount = layerModel.amount,
            type = layerModel.type
        )
    }

    override fun mapToLayerModel(model: AllowanceChartAmountModel): AllowanceChartAmountUIModel {
        return AllowanceChartAmountUIModel(
            date = model.date,
            amount = model.amount,
            type = model.type
        )
    }
}