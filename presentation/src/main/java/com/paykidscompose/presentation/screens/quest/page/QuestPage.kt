package com.paykidscompose.presentation.screens.quest.page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.dummy.dummyQuests
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.Blue2
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.Gray6
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.QuestCardBottomPadding
import com.paykidscompose.presentation.ui.theme.QuestCardHeight
import com.paykidscompose.presentation.ui.theme.QuestCardInnerPadding
import com.paykidscompose.presentation.ui.theme.QuestCardProgressBarBorderWidth
import com.paykidscompose.presentation.ui.theme.QuestCardProgressBarHeight
import com.paykidscompose.presentation.ui.theme.QuestCardProgressBarRound
import com.paykidscompose.presentation.ui.theme.QuestCardRound
import com.paykidscompose.presentation.ui.theme.QuestCardShadowElevation
import com.paykidscompose.presentation.ui.theme.QuestPageDailyQuestDescriptionTextBottomPadding
import com.paykidscompose.presentation.ui.theme.QuestPageDailyQuestDescriptionTextStyle
import com.paykidscompose.presentation.ui.theme.QuestPageDailyQuestTextBottomPadding
import com.paykidscompose.presentation.ui.theme.QuestPageDailyQuestTextStyle
import com.paykidscompose.presentation.ui.theme.QuestPageDailyQuestTextTopPadding
import com.paykidscompose.presentation.ui.theme.QuestPageHorizontalPadding
import com.paykidscompose.presentation.ui.theme.QuestPageQuestCardProgressTextStyle
import com.paykidscompose.presentation.ui.theme.QuestPageQuestCardTitleTextStyle
import com.paykidscompose.presentation.ui.theme.White

@Composable
fun QuestPage() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray5)
            .padding(horizontal = QuestPageHorizontalPadding)
    ) {
        item {
            Text(
                text = stringResource(R.string.text_daily_quest),
                style = QuestPageDailyQuestTextStyle,
                modifier = Modifier.padding(top = QuestPageDailyQuestTextTopPadding, bottom = QuestPageDailyQuestTextBottomPadding)
            )
        }
        item {
            Text(
                text = stringResource(R.string.text_daily_quest_description),
                style = QuestPageDailyQuestDescriptionTextStyle,
                color = Blue1,
                modifier = Modifier.padding(bottom = QuestPageDailyQuestDescriptionTextBottomPadding)
            )
        }
        items(dummyQuests) { quest ->
            QuestCard(
                questTitle = quest.title,
                progress = quest.progress,
                maxProgress = quest.maxProgress,
                modifier = Modifier.padding(bottom = QuestCardBottomPadding)
            )
        }
    }
}

@Composable
fun QuestCard(
    questTitle: String, progress: Int, maxProgress: Int, modifier: Modifier = Modifier
) {
    val isCompleted = progress >= maxProgress

    val cardBackgroundColor = if (isCompleted) Blue2 else White
    val questTitleColor = if (isCompleted) White else Blue1
    val progressBorderColor = if (isCompleted) White else Gray6
    val progressTextColor = if (isCompleted) White else Gray6
    val progressRatio = progress.toFloat() / maxProgress

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(QuestCardHeight)
            .shadow(
                elevation = QuestCardShadowElevation,
                shape = RoundedCornerShape(QuestCardRound),
                ambientColor = Blue2,
                spotColor = Blue2
            ),
        shape = RoundedCornerShape(QuestCardRound),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(QuestCardInnerPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 퀘스트 제목
                Text(
                    text = questTitle,
                    style = QuestPageQuestCardTitleTextStyle,
                    color = questTitleColor,
                )
                // 체크 아이콘
                Icon(
                    painter = painterResource(R.drawable.ic_check_white),
                    contentDescription = null,
                    tint = White,
                )
            }
            // 진행도 바
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(QuestCardProgressBarHeight)
                    .clip(RoundedCornerShape(QuestCardProgressBarRound))
                    .background(White)
                    .border(
                        QuestCardProgressBarBorderWidth,
                        progressBorderColor,
                        RoundedCornerShape(QuestCardProgressBarRound)
                    )
            ) {
                // 진행 바(채워진 부분)
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progressRatio)
                        .clip(RoundedCornerShape(QuestCardProgressBarRound))
                        .background(Blue1)
                )
                // 진행도 텍스트
                Text(
                    text = "$progress/$maxProgress",
                    style = QuestPageQuestCardProgressTextStyle,
                    color = progressTextColor,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview
@Composable
fun QuestPagePreview() {
    PayKidsComposeTheme {
        QuestPage()
    }
}

@Preview
@Composable
fun QuestCardPreview() {
    PayKidsComposeTheme {
        QuestCard(
            "오답노트 2회 풀기", progress = 1, maxProgress = 2, modifier = Modifier
        )
    }
}

@Preview
@Composable
fun QuestCardCompletedPreview() {
    PayKidsComposeTheme {
        QuestCard(
            "오답노트 2회 풀기", progress = 2, maxProgress = 2, modifier = Modifier
        )
    }
}