package com.paykidscompose.presentation.screens.quest.page

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.dummy.dummyAchievements
import com.paykidscompose.presentation.model.achievement.AchievementUIModel
import com.paykidscompose.presentation.ui.theme.AchievementCardDescriptionTextStyle
import com.paykidscompose.presentation.ui.theme.AchievementCardFirstTextSpacerHeight
import com.paykidscompose.presentation.ui.theme.AchievementCardHeight
import com.paykidscompose.presentation.ui.theme.AchievementCardHorizontalPadding
import com.paykidscompose.presentation.ui.theme.AchievementCardImageSize
import com.paykidscompose.presentation.ui.theme.AchievementCardRound
import com.paykidscompose.presentation.ui.theme.AchievementCardSecondText2LineSpacerHeight
import com.paykidscompose.presentation.ui.theme.AchievementCardSecondTextSpacerHeight
import com.paykidscompose.presentation.ui.theme.AchievementCardShadowElevation
import com.paykidscompose.presentation.ui.theme.AchievementCardTitleTextStyle
import com.paykidscompose.presentation.ui.theme.AchievementCardTopPadding
import com.paykidscompose.presentation.ui.theme.AchievementCardWidth
import com.paykidscompose.presentation.ui.theme.AchievementGridArrangementSpace
import com.paykidscompose.presentation.ui.theme.AchievementGridBottomSpacerHeight
import com.paykidscompose.presentation.ui.theme.AchievementGridHorizontalPadding
import com.paykidscompose.presentation.ui.theme.AchievementGridTopSpacerHeight
import com.paykidscompose.presentation.ui.theme.AchievementPageNoBadgeDescriptionTextStyle
import com.paykidscompose.presentation.ui.theme.AchievementPageNoBadgeTextSpacerHeight
import com.paykidscompose.presentation.ui.theme.AchievementPageNoBadgeTextStyle
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.Gray8
import com.paykidscompose.presentation.ui.theme.MyPageCardShadowColor
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.White

@Composable
fun AchievementPage(
    achievements: List<AchievementUIModel> = emptyList()
) {
    val completedAchievements = achievements.filter { it.isCompleted }

    if (completedAchievements.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Gray5)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.text_no_badge1),
                style = AchievementPageNoBadgeTextStyle
            )
            Spacer(modifier = Modifier.height(AchievementPageNoBadgeTextSpacerHeight))
            Text(
                text = stringResource(R.string.text_no_badge2),
                style = AchievementPageNoBadgeDescriptionTextStyle,
                color = Blue1
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Gray5),
            contentPadding = PaddingValues(horizontal = AchievementGridHorizontalPadding),
            verticalArrangement = Arrangement.spacedBy(AchievementGridArrangementSpace),
            horizontalArrangement = Arrangement.spacedBy(AchievementGridArrangementSpace),
        ) {
            items(2) {
                Spacer(modifier = Modifier.height(AchievementGridTopSpacerHeight))
            }
            items(completedAchievements.size) { index ->
                val item = completedAchievements[index]
                AchievementCard(
                    imageUrl = item.imageURL,
                    title = item.name,
                    description = item.description
                )
            }
            items(2) {
                Spacer(modifier = Modifier.height(AchievementGridBottomSpacerHeight))
            }
        }
    }
}

@Composable
fun AchievementCard(
    imageUrl: String,
    title: String,
    description: String
) {
    val context = LocalContext.current
    var lineCount by remember { mutableIntStateOf(1) } // 줄 수 상태 기억

    Card(
        modifier = Modifier
            .size(AchievementCardWidth, AchievementCardHeight)
            .shadow(
                elevation = AchievementCardShadowElevation,
                shape = RoundedCornerShape(AchievementCardRound),
                ambientColor = MyPageCardShadowColor,
                spotColor = MyPageCardShadowColor
            ),
        shape = RoundedCornerShape(AchievementCardRound),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AchievementCardHorizontalPadding)
                .padding(top = AchievementCardTopPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                filterQuality = FilterQuality.High,
                contentDescription = null,
                modifier = Modifier
                    .size(AchievementCardImageSize)
                    .clip(CircleShape),
                onError = {
                    Log.e("Coil", "Failed to load image", it.result.throwable)
                },
                onLoading = {
                    Log.d("Coil", "load onLoading")
                },
                onSuccess = {
                    Log.d("Coil", "load onSuccess")
                }

            )

            Spacer(modifier = Modifier.height(AchievementCardFirstTextSpacerHeight))

            Text(
                text = title,
                style = AchievementCardTitleTextStyle,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(if (lineCount > 1) AchievementCardSecondText2LineSpacerHeight else AchievementCardSecondTextSpacerHeight))

            Text(
                text = description,
                style = AchievementCardDescriptionTextStyle,
                color = Gray8,
                textAlign = TextAlign.Center,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth(),
                onTextLayout = {
                    lineCount = it.lineCount // 줄 수 계산
                }
            )
        }
    }
}

@Preview
@Composable
fun AchievementCardPreview() {
    PayKidsComposeTheme {
        AchievementCard(
            imageUrl = "https://picsum.photos/200", // 임의의 랜덤 이미지
            title = "퀘스트 정복자",
            description = "모든 퀘스트를 완료했어요!"
        )
    }
}

@Preview
@Composable
fun AchievementPagePreview() {
    PayKidsComposeTheme {
        AchievementPage(
            achievements = dummyAchievements
        )
    }
}