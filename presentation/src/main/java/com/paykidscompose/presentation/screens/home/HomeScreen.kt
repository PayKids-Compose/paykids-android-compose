package com.paykidscompose.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.dummy.getStageTitle
import com.paykidscompose.presentation.navigation.bottom.BottomBarItem
import com.paykidscompose.presentation.screens.PayKidsScaffold
import com.paykidscompose.presentation.ui.components.AppBottomBar
import com.paykidscompose.presentation.ui.components.ImageTooltip
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.CardShadowElevation
import com.paykidscompose.presentation.ui.theme.StageCardNumberTextStyle
import com.paykidscompose.presentation.ui.theme.StageCardTitleTextStyle
import com.paykidscompose.presentation.ui.theme.StageCircleBorderWidth
import com.paykidscompose.presentation.ui.theme.StageCircleSize
import com.paykidscompose.presentation.ui.theme.StageDescriptionCardHeight
import com.paykidscompose.presentation.ui.theme.StageDescriptionCardHorizontalPadding
import com.paykidscompose.presentation.ui.theme.StageDescriptionCardRound
import com.paykidscompose.presentation.ui.theme.StageDescriptionCardTextHorizontalPadding
import com.paykidscompose.presentation.ui.theme.StageDescriptionCardTextSpacer
import com.paykidscompose.presentation.ui.theme.StageDescriptionCardTextVerticalPadding
import com.paykidscompose.presentation.ui.theme.StageDescriptionCardVerticalPadding
import com.paykidscompose.presentation.ui.theme.StageHorizontalPadding
import com.paykidscompose.presentation.ui.theme.StageIconSize
import com.paykidscompose.presentation.ui.theme.StageTooltipOffsetX
import com.paykidscompose.presentation.ui.theme.StageTooltipOffsetY
import com.paykidscompose.presentation.ui.theme.StageTopPadding
import com.paykidscompose.presentation.ui.theme.StageVerticalSpace
import com.paykidscompose.presentation.ui.theme.White
import com.paykidscompose.presentation.util.getStageVisuals

private const val totalStageCount = 26
private const val unlockedStageCount = 7
val stageImageSet = listOf(
    R.drawable.ic_home_pig_unlock to R.drawable.ic_home_pig_lock,
    R.drawable.ic_home_coin_unlock to R.drawable.ic_home_coin_lock,
    R.drawable.ic_home_card_unlock to R.drawable.ic_home_card_lock,
    R.drawable.ic_home_acount_unlock to R.drawable.ic_home_acount_lock,
    R.drawable.ic_home_moneybag_unlock to R.drawable.ic_home_moneybag_lock
)

@Composable
fun HomeScreen(
    onStageNumber: (Int) -> Unit = {}
) {
    val scrollState = rememberLazyListState()

    var clickedStageNumber by remember {
        mutableIntStateOf(0)
    }

    val density = LocalDensity.current
    val itemHeightDp = StageCircleSize + StageVerticalSpace
    val itemHeightPx = with(density) { itemHeightDp.toPx() }

    val backgroundOffset by remember {
        derivedStateOf {
            val totalOffsetPx = scrollState.firstVisibleItemIndex * itemHeightPx +
                    scrollState.firstVisibleItemScrollOffset
            with(density) { -(totalOffsetPx * 0.6f).toDp() }
        }
    }
    val context = LocalContext.current

    val tooltipOffset = remember { mutableStateOf<Offset?>(null) }

    val lastClearedStageIndex = 6 // 클리어 스테이지 인덱스
    val selectedStageIndex = remember { mutableIntStateOf(lastClearedStageIndex) }

    Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(R.drawable.bg_home_2)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                filterQuality = FilterQuality.High,
                contentScale = ContentScale.FillWidth,
                clipToBounds = false,
                alignment = Alignment.TopStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = backgroundOffset)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(StageVerticalSpace),
                contentPadding = PaddingValues(horizontal = StageHorizontalPadding)
            ) {
                items(totalStageCount) { index ->
                    if (index == 0) {
                        Spacer(modifier = Modifier.height(StageTopPadding))
                    }

                    val (imageRes, borderColor) = getStageVisuals(
                        index,
                        unlockedStageCount,
                        stageImageSet
                    )
                    val itemOffset = remember { mutableStateOf(Offset(0f, 0f)) }

                    Box(
                        modifier = Modifier
                            .size(StageCircleSize)
                            .background(color = White, shape = CircleShape)
                            .border(
                                width = StageCircleBorderWidth,
                                color = borderColor,
                                shape = CircleShape
                            )
                            .onGloballyPositioned { coordinates ->
                                itemOffset.value = coordinates.positionInWindow()
                            }
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                selectedStageIndex.intValue = index
                                tooltipOffset.value = Offset(itemOffset.value.x, itemOffset.value.y)

                                if (index < unlockedStageCount) {
                                    onStageNumber(index + 1)
                                } else {

                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null, // ripple 제거
                            modifier = Modifier.size(StageIconSize)
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(
                        horizontal = StageDescriptionCardHorizontalPadding,
                        vertical = StageDescriptionCardVerticalPadding
                    )
                    .height(StageDescriptionCardHeight)
                    .shadow(
                        elevation = CardShadowElevation,
                        shape = RoundedCornerShape(StageDescriptionCardRound),
                        ambientColor = Blue1,
                        spotColor = Blue1
                    ),
                shape = RoundedCornerShape(StageDescriptionCardRound),
                colors = CardDefaults.cardColors(
                    containerColor = White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = StageDescriptionCardTextHorizontalPadding,
                            vertical = StageDescriptionCardTextVerticalPadding
                        )
                ) {
                    Text(
                        text = selectedStageIndex.intValue.let {
                            stringResource(R.string.text_stage_number, it + 1)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        style = StageCardNumberTextStyle
                    )
                    Spacer(modifier = Modifier.height(StageDescriptionCardTextSpacer))
                    Text(
                        text = selectedStageIndex.intValue?.let { getStageTitle(it) }
                            ?: stringResource(R.string.text_stage_title),
                        modifier = Modifier.fillMaxWidth(),
                        style = StageCardTitleTextStyle
                    )
                }
            }

            // 화면을 클릭하면 툴팁 닫힘
            if (tooltipOffset.value != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            onClick = {
                                tooltipOffset.value = null
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                )
            }

            // 툴팁 표시
            tooltipOffset.value?.let { offset ->
                ImageTooltip(
                    tooltipText = stringResource(R.string.text_tooltip_start),
                    modifier = Modifier
                        .clickable {
                            tooltipOffset.value = null
                        }
                        .offset {
                            IntOffset(
                                offset.x.toInt() - StageTooltipOffsetX,
                                offset.y.toInt() - StageTooltipOffsetY
                            )
                        }
                )
            }

    }
}


@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}