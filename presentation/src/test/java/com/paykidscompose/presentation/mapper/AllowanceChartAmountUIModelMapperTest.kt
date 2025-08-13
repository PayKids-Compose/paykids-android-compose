package com.paykidscompose.presentation.mapper

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.model.allowance.AllowanceChartAmountModel
import com.paykidscompose.presentation.mapper.allowance.AllowanceChartAmountUIModelMapper
import com.paykidscompose.presentation.model.allowance.AllowanceChartAmountUIModel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class AllowanceChartAmountUIModelMapperTest {

    @Test
    fun `mapToModel_givenUIModel_returnsEquivalentCommonModel`() {
        // Given
        val uiModel = AllowanceChartAmountUIModel(
            date = LocalDate.of(2025,8,13),
            amount = 10000,
            type = AllowanceType.EXPENSE
        )

        // When
        val model = AllowanceChartAmountUIModelMapper.mapToModel(uiModel)

        // Then
        assertEquals(uiModel.date, model.date)
        assertEquals(uiModel.amount, model.amount)
        assertEquals(uiModel.type, model.type)
    }

    @Test
    fun `mapToLayerModel_givenCommonModel_returnsEquivalentUIModel`() {
        // Given
        val model = AllowanceChartAmountModel(
            date = LocalDate.of(2025,8,13),
            amount = 10000,
            type = AllowanceType.INCOME
        )

        // When
        val uiModel = AllowanceChartAmountUIModelMapper.mapToLayerModel(model)

        // Then
        assertEquals(model.date, uiModel.date)
        assertEquals(model.amount, uiModel.amount)
        assertEquals(model.type, uiModel.type)
    }
}