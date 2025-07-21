package com.paykidscompose.data.mapper.allowance

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.mapper.ModelMapper
import com.paykidscompose.common.model.allowance.AllowanceChartAmountModel
import com.paykidscompose.data.model.allowance.AllowanceChartAmountDTO
import java.time.LocalDate

object AllowanceChartAmountMapper : ModelMapper<AllowanceChartAmountModel, AllowanceChartAmountDTO> {
    override fun mapToModel(layerModel: AllowanceChartAmountDTO): AllowanceChartAmountModel {
        return AllowanceChartAmountModel(
            date = LocalDate.parse(layerModel.date),
            amount = layerModel.amount,
            type = AllowanceType.valueOf(layerModel.allowanceType)
        )
    }

    override fun mapToLayerModel(model: AllowanceChartAmountModel): AllowanceChartAmountDTO {
        return AllowanceChartAmountDTO(
            date = model.date.toString(),
            amount = model.amount,
            allowanceType = model.type.name
        )
    }

}