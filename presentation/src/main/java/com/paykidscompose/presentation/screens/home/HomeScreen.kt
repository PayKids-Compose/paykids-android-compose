package com.paykidscompose.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.theme.Dimens
import com.paykidscompose.presentation.ui.theme.White
import com.paykidscompose.presentation.ui.util.getStageVisuals

private const val totalStageCount = 26
private const val unlockedStageCount = 7
val stageImageSet = listOf(
    R.drawable.ic_home_pig_unlock to R.drawable.ic_home_pig_lock,
    R.drawable.ic_home_coin_unlock to R.drawable.ic_home_coin_lock,
    R.drawable.ic_home_card_unlock to R.drawable.ic_home_card_lock,
    R.drawable.ic_home_acount_unlock to R.drawable.ic_home_acount_lock,
    R.drawable.ic_home_moneybag_unlock to R.drawable.ic_home_moneybag_lock
)

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    val scrollState = rememberLazyListState()
    val density = LocalDensity.current
    val itemHeightDp = Dimens.StageCircleSize + Dimens.StageVerticalSpace
    val itemHeightPx = with(density) { itemHeightDp.toPx() }
    val backgroundOffset by remember {
        derivedStateOf {
            val totalOffsetPx = scrollState.firstVisibleItemIndex * itemHeightPx +
                    scrollState.firstVisibleItemScrollOffset
            with(density) { -(totalOffsetPx * 0.6f).toDp() } // 60%만 따라가게 하면 더 자연스럽게
        }
    }
    val context = LocalContext.current

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
            modifier = Modifier
                .fillMaxWidth()
                .height(800.dp) // 필요에 따라
//                .offset(x = 0.dp, y = lineHeight.value),
                .offset(y = backgroundOffset)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Dimens.StageTopPadding),
            state = scrollState,
            verticalArrangement = Arrangement.spacedBy(Dimens.StageVerticalSpace),
            contentPadding = PaddingValues(horizontal = Dimens.StageHorizontalPadding)
        ) {
            items(totalStageCount) { index ->
                val (imageRes, borderColor) = getStageVisuals(index, unlockedStageCount, stageImageSet)

                Box(
                    modifier = Modifier
                        .size(Dimens.StageCircleSize)
                        .background(color = White, shape = CircleShape)
                        .border(
                            width = Dimens.StageCircleBorderWidth,
                            color = borderColor,
                            shape = CircleShape
                        )
                        .clickable {
                            if (index < unlockedStageCount) {

                            } else {

                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.StageIconSize)
                    )
                }
            }
        }

    }
}

@Composable
fun rememberCurrentOffset(state: LazyListState): androidx.compose.runtime.State<Int> {
    val position = remember { derivedStateOf { state.firstVisibleItemIndex } }
    val itemOffset = remember { derivedStateOf { state.firstVisibleItemScrollOffset } }
    val lastPosition = rememberPrevious(position.value)
    val lastItemOffset = rememberPrevious(itemOffset.value)
    val currentOffset = remember { mutableStateOf(0) }

    LaunchedEffect(position.value, itemOffset.value) {
        if (lastPosition == null || position.value == 0) {
            currentOffset.value = itemOffset.value
        } else if (lastPosition == position.value) {
            currentOffset.value += (itemOffset.value - (lastItemOffset ?: 0))
        } else if (lastPosition > position.value) {
            currentOffset.value -= (lastItemOffset ?: 0)
        } else { // lastPosition.value < position.value
            currentOffset.value += itemOffset.value
        }
    }

    return currentOffset
}


@Composable
fun <T> rememberPrevious(
    current: T,
    shouldUpdate: (prev: T?, curr: T) -> Boolean = { a: T?, b: T -> a != b },
): T? {
    val ref = rememberRef<T>()

    // launched after render, so the current render will have the old value anyway
    SideEffect {
        if (shouldUpdate(ref.value, current)) {
            ref.value = current
        }
    }

    return ref.value
}

@Composable
fun <T> rememberRef(): MutableState<T?> {
    // for some reason it always recreated the value with vararg keys,
    // leaving out the keys as a parameter for remember for now
    return remember() {
        object : MutableState<T?> {
            override var value: T? = null

            override fun component1(): T? = value

            override fun component2(): (T?) -> Unit = { value = it }
        }
    }
}
