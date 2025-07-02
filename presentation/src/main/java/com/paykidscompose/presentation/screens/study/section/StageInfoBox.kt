package com.paykidscompose.presentation.screens.study.section

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paykidscompose.presentation.ui.theme.Gray7
import com.paykidscompose.presentation.ui.theme.StudyStageNumberBoxBorderWidth
import com.paykidscompose.presentation.ui.theme.StudyStageNumberBoxHorizontalPadding
import com.paykidscompose.presentation.ui.theme.StudyStageNumberBoxRound
import com.paykidscompose.presentation.ui.theme.StudyStageNumberBoxTextStyle
import com.paykidscompose.presentation.ui.theme.StudyStageNumberBoxVerticalPadding
import com.paykidscompose.presentation.ui.theme.White

@Composable
fun StageInfoBox(stage: String) {
    Box(
        modifier = Modifier
            .background(White, shape = RoundedCornerShape(StudyStageNumberBoxRound))
            .border(
                width = StudyStageNumberBoxBorderWidth,
                color = Gray7,
                shape = RoundedCornerShape(StudyStageNumberBoxRound)
            )
            .padding(
                horizontal = StudyStageNumberBoxHorizontalPadding,
                vertical = StudyStageNumberBoxVerticalPadding
            )
    ) {
        Text(
            text = stage, style = StudyStageNumberBoxTextStyle, color = Gray7
        )
    }
}