package com.paykidscompose.presentation.screens.quiz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.model.QuizType
import com.paykidscompose.presentation.ui.components.AppTopBar
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.CardShadowElevation
import com.paykidscompose.presentation.ui.theme.Gray3
import com.paykidscompose.presentation.ui.theme.ImageQuizCardImageRound
import com.paykidscompose.presentation.ui.theme.ImageQuizCardImageSize
import com.paykidscompose.presentation.ui.theme.ImageQuizCardRound
import com.paykidscompose.presentation.ui.theme.ImageQuizCardRowSpacer
import com.paykidscompose.presentation.ui.theme.ImageQuizCardSize
import com.paykidscompose.presentation.ui.theme.ImageQuizCardSpaceBetween
import com.paykidscompose.presentation.ui.theme.ImageQuizCardTextSpacer
import com.paykidscompose.presentation.ui.theme.QuizAnswerTextStyle
import com.paykidscompose.presentation.ui.theme.QuizAppBarTextStyle
import com.paykidscompose.presentation.ui.theme.QuizQuestionTextSpacer
import com.paykidscompose.presentation.ui.theme.QuizQuestionTextStyle
import com.paykidscompose.presentation.ui.theme.StartAndEndPadding

@Preview(showBackground = true)
@Composable
fun QuizScreen(
    onBackClick: () -> Unit = {},
    quizType: QuizType = QuizType.IMAGE,
    quizNumber: Int = 2,
    totalQuizCount: Int = 7,
    question: String = "10,000원 권 지폐에는\n어떤 인물이 그려져 있을까요?",
    imgChoices: List<Pair<Int, String>> = listOf(
        R.drawable.img_quiz_item_default to "세종대왕",
        R.drawable.img_quiz_item_default to "신사임당",
        R.drawable.img_quiz_item_default to "퇴계 이황",
        R.drawable.img_quiz_item_default to "이순신"
    ),
    answer: String = "A",
    onChoiceClick: (Int) -> Unit = {}
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.text_quiz_number, quizNumber, totalQuizCount),
                titleStyle = QuizAppBarTextStyle,
                showBackButton = true,
                onBackClick = onBackClick,
                iconTint = Black
            )
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding()
                )
        ) {
            AsyncImage(
                model = R.drawable.bg_quiz_default,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = StartAndEndPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = question,
                    style = QuizQuestionTextStyle,
                    color = Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(QuizQuestionTextSpacer))

                // 2x2 Grid: Row 2개, 각 Row 안에 2개의 선택지
                for (row in 0 until 2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            ImageQuizCardSpaceBetween, Alignment.CenterHorizontally
                        ),
                    ) {
                        for (col in 0 until 2) {
                            val index = row * 2 + col
                            val (imageRes, label) = imgChoices.getOrNull(index) ?: continue

                            Card(
                                modifier = Modifier
                                    .size(ImageQuizCardSize.first, ImageQuizCardSize.second)
                                    .clickable { onChoiceClick(index) }
                                    .shadow(
                                        elevation = CardShadowElevation,
                                        shape = RoundedCornerShape(ImageQuizCardRound),
                                        ambientColor = Gray3,
                                        spotColor = Gray3
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
        }
    }

}