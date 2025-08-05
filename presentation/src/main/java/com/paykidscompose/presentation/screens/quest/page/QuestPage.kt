package com.paykidscompose.presentation.screens.quest.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.dummy.dummyQuests
import com.paykidscompose.presentation.model.quest.QuestUIModel
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
import kotlinx.coroutines.delay

@Composable
fun QuestPage(
    quests: List<QuestUIModel> = emptyList(),
    onQuestRemove: (String) -> Unit = {}
) {
    if (quests.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = R.drawable.bg_quest_all_completed,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(R.string.text_daily_quest_all_completed),
                style = QuestPageDailyQuestTextStyle,
                textAlign = TextAlign.Center,
            )
        }
    } else {
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
                    modifier = Modifier.padding(
                        top = QuestPageDailyQuestTextTopPadding,
                        bottom = QuestPageDailyQuestTextBottomPadding
                    )
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
            items(quests, key = { it.name }) { quest ->
                QuestCardWithSlideOut(
                    quest = quest,
                    modifier = Modifier.padding(bottom = QuestCardBottomPadding),
                    onRemove = {
                        onQuestRemove(quest.name)
                    }
                )
            }
        }
    }
}

@Composable
fun QuestCardWithSlideOut(
    quest: QuestUIModel,
    modifier: Modifier = Modifier,
    onRemove: () -> Unit = {}
) {
    var visible by remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = visible,
        exit = slideOutVertically(
            targetOffsetY = { -it }, // 상단으로 밀려나기
            animationSpec = tween(durationMillis = 500)
        ) + fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        QuestCard(
            quest = quest,
            modifier = modifier,
            onClickIfCompleted = {
                if (quest.isComplete) {
                    visible = false // 애니메이션 시작
                }
            }
        )
    }

    // visible이 false가 된 후에 딜레이를 주고 리스트에서 제거 콜백 호출
    if (!visible) {
        LaunchedEffect(Unit) {
            delay(500) // 애니메이션 재생 시간 동안 대기
            onRemove()
        }
    }
}

@Composable
fun QuestCard(
    quest: QuestUIModel,
    modifier: Modifier = Modifier,
    onClickIfCompleted: () -> Unit = {}
) {
    val cardBackgroundColor = if (quest.isComplete) Blue2 else White
    val questTitleColor = if (quest.isComplete) White else Blue1
    val progressBorderColor = if (quest.isComplete) White else Gray6
    val progressTextColor = if (quest.isComplete) White else Gray6
    val progressRatio = quest.count.toFloat() / quest.maxCount

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(QuestCardHeight)
            .shadow(
                elevation = QuestCardShadowElevation,
                shape = RoundedCornerShape(QuestCardRound),
                ambientColor = Blue2,
                spotColor = Blue2
            )
            .clickable(enabled = quest.isComplete) {
                onClickIfCompleted()
            },
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
                    text = quest.name,
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
                    text = "${quest.count}/${quest.maxCount}",
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
        QuestPage(
            quests = dummyQuests
        )
    }
}

@Preview
@Composable
fun QuestCardPreview() {
    PayKidsComposeTheme {
        QuestCard(
            QuestUIModel(
                name = "오답노트 2회 풀기",
                count = 1,
                maxCount = 2,
                isComplete = false
            ),
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun QuestCardCompletedPreview() {
    PayKidsComposeTheme {
        QuestCard(
            QuestUIModel(
                name = "오답노트 2회 풀기",
                count = 2,
                maxCount = 2,
                isComplete = true
            ),
            modifier = Modifier
        )
    }
}