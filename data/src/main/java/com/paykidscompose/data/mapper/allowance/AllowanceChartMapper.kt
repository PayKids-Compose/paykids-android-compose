package com.paykidscompose.data.mapper.allowance

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.data.model.allowance.AllowanceChartDTO
import java.time.LocalDate

object AllowanceChartMapper : ModelMapper<AllowanceChartModel, AllowanceChartDTO> {
    override fun mapToModel(layerModel: AllowanceChartDTO): AllowanceChartModel {
        return AllowanceChartModel(
            id = layerModel.id,
            date = LocalDate.parse(layerModel.date),
            type = AllowanceType.valueOf(layerModel.allowanceType),
            category = layerModel.category,
            amount = layerModel.amount,
            memo = layerModel.memo
        )
    }

    override fun mapToLayerModel(model: AllowanceChartModel): AllowanceChartDTO {
        return AllowanceChartDTO(
            id = model.id,
            date = model.date.toString(),
            allowanceType = model.type.name,
            category = model.category,
            amount = model.amount,
            memo = model.memo
        )
    }

}