package com.paykidscompose.presentation.mapper.allowance

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.presentation.model.allowance.AllowanceChartUIModel
import com.paykidscompose.presentation.util.formatAmount

object AllowanceChartUIModelMapper : ModelMapper<AllowanceChartModel, AllowanceChartUIModel> {
    override fun mapToModel(layerModel: AllowanceChartUIModel): AllowanceChartModel {
        return AllowanceChartModel(
            id = layerModel.id,
            date = layerModel.date,
            type = layerModel.type,
            category = layerModel.category,
            amount = layerModel.amount,
            memo = layerModel.memo
        )
    }

    override fun mapToLayerModel(model: AllowanceChartModel): AllowanceChartUIModel {
        val formatAmount = when (model.type) {
            AllowanceType.EXPENSE -> "-${formatAmount(model.amount)}원"
            AllowanceType.INCOME -> "+${formatAmount(model.amount)}원"
        }

        return AllowanceChartUIModel(
            id = model.id,
            date = model.date,
            category = model.category,
            amountFormatted = formatAmount,
            amount = model.amount,
            memo = model.memo,
            type = model.type
        )
    }
}