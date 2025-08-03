package com.paykidscompose.presentation.util

import androidx.compose.ui.graphics.Color
import com.paykidscompose.presentation.model.allowance.AllowanceChartCategoryUIModel
import com.paykidscompose.presentation.model.allowance.CategoryProgressBarUIModel

val defaultColors = listOf(
    Color(0xFFE57373), Color(0xFFF06292), Color(0xFFBA68C8), Color(0xFF9575CD),
    Color(0xFF7986CB), Color(0xFF64B5F6), Color(0xFF4FC3F7), Color(0xFF4DD0E1),
    Color(0xFF4DB6AC), Color(0xFF81C784), Color(0xFFAED581), Color(0xFFDCE775),
    Color(0xFFFFD54F), Color(0xFFFFB74D), Color(0xFFFF8A65), Color(0xFFA1887F),
    Color(0xFF90A4AE), Color(0xFFF48FB1), Color(0xFFCE93D8), Color(0xFFB39DDB),
    Color(0xFF9FA8DA), Color(0xFF90CAF9), Color(0xFF80DEEA), Color(0xFF80CBC4),
    Color(0xFFA5D6A7), Color(0xFFE6EE9C), Color(0xFFFFF59D), Color(0xFFFFE082),
    Color(0xFFFFCC80), Color(0xFFFFAB91), Color(0xFFBCAAA4), Color(0xFFB0BEC5)
)

fun generateColorFromCategory(str: String): Color {
    val hash = str.hashCode()
    val index = (hash and 0x7FFFFFFF) % defaultColors.size
    return defaultColors[index]
}

fun assignColorsToCategories(
    data: List<AllowanceChartCategoryUIModel>
): List<CategoryProgressBarUIModel> {
    return data.map { item ->
        CategoryProgressBarUIModel(
            name = item.category,
            percent = item.percent / 100f,
            color = generateColorFromCategory(item.category)
        )
    }
}
