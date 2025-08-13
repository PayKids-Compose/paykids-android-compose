package com.paykidscompose.presentation.mapper

import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.model.allowance.AllowanceChartModel
import com.paykidscompose.presentation.mapper.allowance.AllowanceChartUIModelMapper
import com.paykidscompose.presentation.model.allowance.AllowanceChartUIModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class AllowanceChartUIModelMapperTest {

    private lateinit var uiModel: AllowanceChartUIModel

    private lateinit var commonModel: AllowanceChartModel

    @Before
    fun setUp() {
        commonModel = AllowanceChartModel(
            id = 1,
            date = LocalDate.of(2025, 8, 13),
            type = AllowanceType.EXPENSE,
            category = "Food",
            amount = 12000,
            memo = "롯데리아를 사먹었어!"
        )

        uiModel = AllowanceChartUIModel(
            id = 1,
            date = LocalDate.of(2025, 8, 13),
            type = AllowanceType.EXPENSE,
            category = "Food",
            amountFormatted = "12,000원",
            amount = 12000,
            memo = "버거킹을 먹었어!"
        )
    }

    @Test
    fun `mapToModel_givenUIModel_returnsEquivalentCommonModel`() {
        val model = AllowanceChartUIModelMapper.mapToModel(uiModel)

        assertEquals(uiModel.id, model.id)
        assertEquals(uiModel.date, model.date)
        assertEquals(uiModel.type, model.type)
        assertEquals(uiModel.category, model.category)
        assertEquals(uiModel.amount, model.amount)
        assertEquals(uiModel.memo, model.memo)
    }

    @Test
    fun `mapToLayerModel_givenCommonModel_returnsEquivalentUIModel`() {
        val uiModel = AllowanceChartUIModelMapper.mapToLayerModel(commonModel)

        assertEquals(commonModel.id, uiModel.id)
        assertEquals(commonModel.date, uiModel.date)
        assertEquals(commonModel.type, uiModel.type)
        assertEquals(commonModel.category, uiModel.category)
        assertEquals(commonModel.amount, uiModel.amount)
        assertEquals("-12,000원", uiModel.amountFormatted)
        assertEquals(commonModel.memo, uiModel.memo)
    }
}