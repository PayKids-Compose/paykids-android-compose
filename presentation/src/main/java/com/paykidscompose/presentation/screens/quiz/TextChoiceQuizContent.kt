package com.paykidscompose.presentation.screens.quiz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paykidscompose.presentation.ui.state.QuizResultState
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.CardShadowElevation
import com.paykidscompose.presentation.ui.theme.Gray3
import com.paykidscompose.presentation.ui.theme.ImageQuizCardRound
import com.paykidscompose.presentation.ui.theme.ImageQuizCardRowSpacer
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.QuizAnswerTextStyle
import com.paykidscompose.presentation.ui.theme.Red
import com.paykidscompose.presentation.ui.theme.TextChoiceQuizCardTextHorizontalPadding
import com.paykidscompose.presentation.ui.theme.TextChoiceQuizCardTextVerticalPadding

@Composable
fun TextChoiceQuizContent(
    textChoices: List<String>,
    answer: String,
    selectedIndex: Int?,
    isCorrect: QuizResultState,
    onChoiceClick: (Int) -> Unit
) {
    val correctIndex = answer[0] - 'A'
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (index in 0 until textChoices.size) {
            val isSelected = selectedIndex == index
            val isCorrectCard = index == correctIndex

            val shadowColor = when {
                isSelected && isCorrect == QuizResultState.WRONG -> Red
                isCorrectCard && isCorrect == QuizResultState.WRONG -> Blue1
                isSelected && isCorrect == QuizResultState.CORRECT -> Blue1
                else -> Gray3
            }

            Card(
                modifier = Modifier
                    .clickable(enabled = isCorrect == QuizResultState.DEFAULT) {
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
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = TextChoiceQuizCardTextHorizontalPadding,
                            vertical = TextChoiceQuizCardTextVerticalPadding
                        ),
                    text = textChoices[index],
                    color = Black,
                    style = QuizAnswerTextStyle
                )
            }
            Spacer(modifier = Modifier.height(ImageQuizCardRowSpacer))
        }
    }
}

@Preview
@Composable
fun TextChoiceQuizContentPreview(
) {
    PayKidsComposeTheme {
        Column(modifier = Modifier.height(500.dp)) {
            TextChoiceQuizContent(
                textChoices = listOf(
                    "10,000원 권 지폐에는 조선 시대의 왕이자 한글을 창시한 세종대왕이 그려져 있다.",
                    "10,000원 권 지폐에는 BTS가 그려져 있다.",
                    "로또 되고 싶다♡"
                ),
                answer = "A",
                selectedIndex = null,
                isCorrect = QuizResultState.DEFAULT,
                onChoiceClick = {}
            )
        }
    }
}