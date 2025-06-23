package com.paykidscompose.presentation.util


import androidx.compose.ui.graphics.Color
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.Gray2

fun getStageVisuals(
    index: Int,
    unlockedStageCount: Int,
    imageSet: List<Pair<Int, Int>>
): Pair<Int, Color> {
    val isUnlocked = index < unlockedStageCount
    val imagePair = imageSet[index % imageSet.size]
    val imageRes = if (isUnlocked) imagePair.first else imagePair.second
    val borderColor = if (isUnlocked) Blue1 else Gray2
    return imageRes to borderColor
}