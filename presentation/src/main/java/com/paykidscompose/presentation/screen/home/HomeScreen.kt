package com.paykidscompose.presentation.screen.home

import android.widget.Toast
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.screen.home.constants.StageFirstItemOffset
import com.paykidscompose.presentation.screen.home.constants.StageOffsetPattern
import com.paykidscompose.presentation.screen.home.constants.stageImageSet
import com.paykidscompose.presentation.ui.components.ImageTooltip
import com.paykidscompose.presentation.ui.components.ScreenLoading
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.CardShadowElevation
import com.paykidscompose.presentation.ui.theme.Gray2
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.StageBottomPadding
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
import com.paykidscompose.presentation.ui.theme.StageIconSize
import com.paykidscompose.presentation.ui.theme.StageStartAndEndPadding
import com.paykidscompose.presentation.ui.theme.StageTooltipImageWidth
import com.paykidscompose.presentation.ui.theme.StageTopPadding
import com.paykidscompose.presentation.ui.theme.StageVerticalSpace
import com.paykidscompose.presentation.ui.theme.White

@Composable
fun Home(
    homeViewModel: HomeViewModel = viewModel(),
    onNavigateToQuiz: (Int, String) -> Unit = { _, _ -> }
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val tooltipOffset = remember { mutableStateOf<Offset?>(null) }

    LaunchedEffect(Unit) {
        homeViewModel.loadAllData()
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            when (it) {
                is PayKidsException.SnackBarException -> {
                }

                is PayKidsException.DialogException -> {
                }

                is PayKidsException.ToastException -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }

            homeViewModel.clearError()
        }
    }

    when {
        uiState.isLoading -> {
            ScreenLoading()
        }

        else -> {
            HomeScreen(
                selectedStageIndex = uiState.selectedStageIndex,
                onStageSelected = { homeViewModel.onStageSelected(it) },
                tooltipOffset = tooltipOffset.value,
                onTooltipOffsetChange = { tooltipOffset.value = it },
                onNavigateToQuiz = onNavigateToQuiz,
                totalStageCount = uiState.totalStageCount,
                unlockedStageCount = uiState.unlockedStageNumber,
                stageTitle = uiState.stageTitle
            )
        }
    }

}

@Composable
fun HomeScreen(
    totalStageCount: Int,
    unlockedStageCount: Int,
    stageTitle: String,
    selectedStageIndex: Int,
    onStageSelected: (Int) -> Unit,
    tooltipOffset: Offset?,
    onTooltipOffsetChange: (Offset?) -> Unit,
    onNavigateToQuiz: (Int, String) -> Unit = { _, _ -> }
) {
    val scrollState = rememberLazyListState()
    val density = LocalDensity.current
    val context = LocalContext.current

    val itemHeightDp = StageCircleSize + StageVerticalSpace
    val itemHeightPx = with(density) { itemHeightDp.toPx() }

    val backgroundOffset by remember {
        derivedStateOf {
            val totalOffsetPx = scrollState.firstVisibleItemIndex * itemHeightPx +
                    scrollState.firstVisibleItemScrollOffset
            with(density) { -totalOffsetPx }
        }
    }

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
                .graphicsLayer {
                    translationY = backgroundOffset
                }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(StageVerticalSpace),
            contentPadding = PaddingValues(
                start = StageStartAndEndPadding,
                end = StageStartAndEndPadding,
                top = StageTopPadding + WindowInsets.statusBars.asPaddingValues()
                    .calculateTopPadding(),
                bottom = StageBottomPadding
            )
        ) {
            items(totalStageCount) { index ->
                val (imageRes, borderColor) = getStageVisuals(
                    index,
                    unlockedStageCount,
                    stageImageSet
                )
                val stageCenterBottomOffset = remember { mutableStateOf(Offset(0f, 0f)) }
                val stageHorizontalOffset = getStageHorizontalOffset(index)

                Box( // 아이템별 스테이지 가로 오프셋 적용을 위한 박스
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(StageCircleSize),
                    contentAlignment = Alignment.Center
                ) {
                    Box( // 스테이지 커스텀을 위한 박스
                        modifier = Modifier
                            .offset(x = stageHorizontalOffset)
                            .size(StageCircleSize)
                            .background(color = White, shape = CircleShape)
                            .border(
                                width = StageCircleBorderWidth,
                                color = borderColor,
                                shape = CircleShape
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onStageSelected(index)
                                if (index + 1 <= unlockedStageCount) {
                                    onTooltipOffsetChange(stageCenterBottomOffset.value)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null, // ripple 제거
                            modifier = Modifier
                                .size(StageIconSize)
                                .onGloballyPositioned { coordinates ->
                                    val stagePosition = coordinates.positionInWindow()
                                    val stageWidthHalf = coordinates.size.width / 2
                                    val stageHeight = coordinates.size.height

                                    stageCenterBottomOffset.value = Offset(
                                        x = stagePosition.x + stageWidthHalf,
                                        y = stagePosition.y + stageHeight
                                    )
                                }
                        )
                    }
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
                    text = stringResource(
                        R.string.text_stage_number,
                        selectedStageIndex + 1
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    style = StageCardNumberTextStyle
                )
                Spacer(modifier = Modifier.height(StageDescriptionCardTextSpacer))
                Text(
                    text = stageTitle,
                    modifier = Modifier.fillMaxWidth(),
                    style = StageCardTitleTextStyle
                )
            }
        }

        // 화면을 클릭하면 툴팁 닫힘
        if (tooltipOffset != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        onClick = {
                            onTooltipOffsetChange(null)
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            )
        }

        // 툴팁 표시
        tooltipOffset?.let { offset ->
            val tooltipPosition = with(LocalDensity.current) {
                DpOffset(
                    offset.x.toDp(),
                    offset.y.toDp()
                )
            }
            ImageTooltip(
                tooltipText = stringResource(R.string.text_tooltip_start),
                modifier = Modifier
                    .offset(tooltipPosition.x - StageTooltipImageWidth / 2, tooltipPosition.y)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onNavigateToQuiz(selectedStageIndex + 1, stageTitle)
                    }
            )
        }
    }
}

private fun getStageVisuals(
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

private fun getStageHorizontalOffset(index: Int): Dp {
    return when (index) {
        0 -> StageFirstItemOffset
        else -> StageOffsetPattern[(index - 1) % StageOffsetPattern.size]
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    PayKidsComposeTheme {
        Home()
    }
}