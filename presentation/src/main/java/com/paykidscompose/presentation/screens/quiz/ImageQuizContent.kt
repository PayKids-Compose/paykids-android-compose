package com.paykidscompose.presentation.screens.quiz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.type.QuizResultType
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.CardShadowElevation
import com.paykidscompose.presentation.ui.theme.Gray3
import com.paykidscompose.presentation.ui.theme.ImageQuizCardImageRound
import com.paykidscompose.presentation.ui.theme.ImageQuizCardImageSize
import com.paykidscompose.presentation.ui.theme.ImageQuizCardRatio
import com.paykidscompose.presentation.ui.theme.ImageQuizCardRound
import com.paykidscompose.presentation.ui.theme.ImageQuizCardRowSpacer
import com.paykidscompose.presentation.ui.theme.ImageQuizCardSpaceBetween
import com.paykidscompose.presentation.ui.theme.ImageQuizCardTextSpacer
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.QuizAnswerTextStyle
import com.paykidscompose.presentation.ui.theme.Red
import kotlin.Int
import kotlin.Pair

@Composable
fun ImageQuizContent(
    imgChoices: List<Pair<Int, String>>,
    answer: String,
    selectedIndex: Int?,
    isCorrect: QuizResultType,
    onChoiceClick: (Int) -> Unit
) {
    val correctIndex = answer[0] - 'A'

    // 2x2 Grid: Row 2개, 각 Row 안에 2개의 선택지
    for (row in 0 until 2) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(ImageQuizCardSpaceBetween)
        ) {
            for (col in 0 until 2) {
                val index = row * 2 + col
                val (imageRes, label) = imgChoices.getOrNull(index) ?: continue

                val isSelected = selectedIndex == index
                val isCorrectCard = index == correctIndex

                val shadowColor = when {
                    isSelected && isCorrect == QuizResultType.WRONG -> Red
                    isCorrectCard && isCorrect == QuizResultType.WRONG -> Blue1
                    isSelected && isCorrect == QuizResultType.CORRECT -> Blue1
                    else -> Gray3
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(ImageQuizCardRatio)
                        .clickable(enabled = isCorrect == QuizResultType.DEFAULT) {
                            onChoiceClick(index)
                        }
                        .shadow(
                            elevation = CardShadowElevation,
                            shape = RoundedCornerShape(ImageQuizCardRound),
                            ambientColor = shadowColor,
                            spotColor = shadowColor
                        ),
                    shape = RoundedCornerShape(ImageQuizCardRound),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = imageRes,
                            contentDescription = label,
                            modifier = Modifier
                                .size(ImageQuizCardImageSize)
                                .clip(RoundedCornerShape(ImageQuizCardImageRound)),
                            contentScale = ContentScale.Fit,
                        )
                        Spacer(modifier = Modifier.height(ImageQuizCardTextSpacer))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = label,
                            color = Black,
                            style = QuizAnswerTextStyle,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(ImageQuizCardRowSpacer))
    }
}

@Preview
@Composable
fun ImageQuizContentPreview() {
    PayKidsComposeTheme {
        Column(modifier = Modifier.height(500.dp)) {
            ImageQuizContent(
                imgChoices = listOf(
                    R.drawable.img_quiz_item_default to "세종대왕",
                    R.drawable.img_quiz_item_default to "신사임당",
                    R.drawable.img_quiz_item_default to "퇴계 이황",
                    R.drawable.img_quiz_item_default to "이순신"
                ),
                answer = "A",
                selectedIndex = null,
                isCorrect = QuizResultType.DEFAULT,
                onChoiceClick = {}
            )
        }
    }
}