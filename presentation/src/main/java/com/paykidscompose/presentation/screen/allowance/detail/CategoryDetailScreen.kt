package com.paykidscompose.presentation.screen.allowance.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.paykidscompose.common.enums.AllowanceType
import com.paykidscompose.common.exception.PayKidsException
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.base.UIEvent
import com.paykidscompose.presentation.model.allowance.AllowanceChartUIModel
import com.paykidscompose.presentation.ui.components.AllowanceInputDialog
import com.paykidscompose.presentation.ui.components.PopupDialog
import com.paykidscompose.presentation.ui.components.ScreenLoading
import com.paykidscompose.presentation.ui.components.util.PopupType
import com.paykidscompose.presentation.ui.theme.AllowanceDiaryHeadMonthTextStyle
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.Blue1
import com.paykidscompose.presentation.ui.theme.CategoryDetailItemAddTextStyle
import com.paykidscompose.presentation.ui.theme.CategoryDetailItemAmountTextStyle
import com.paykidscompose.presentation.ui.theme.CategoryDetailItemCategoryTextStyle
import com.paykidscompose.presentation.ui.theme.CategoryDetailItemMemoTextStyle
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenItemStartEndPadding
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenItemTopBottomPadding
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenSpacer24
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenSpacer53
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenSpacer6
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenSpacer8
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenStartEndPadding
import com.paykidscompose.presentation.ui.theme.CategoryDetailScreenTopPadding
import com.paykidscompose.presentation.ui.theme.CategoryDetailTitleTextStyle
import com.paykidscompose.presentation.ui.theme.Gray1
import com.paykidscompose.presentation.ui.theme.Gray5
import com.paykidscompose.presentation.ui.theme.Gray7
import com.paykidscompose.presentation.ui.theme.MyPageCardShadowColor
import com.paykidscompose.presentation.ui.theme.ShadowElevation16
import com.paykidscompose.presentation.ui.theme.Shape10
import com.paykidscompose.presentation.ui.theme.White
import com.paykidscompose.presentation.ui.theme.White2
import com.paykidscompose.presentation.util.formatAmount
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@Composable
fun CategoryDetail(
    year: Int,
    month: Int,
    category: String,
    type: AllowanceType,
    viewModel: CategoryDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    if (uiState.showAddTransactionDialog) {
        AllowanceInputDialog(
            chartUIModel = AllowanceChartUIModel(
                id = 0,
                date = LocalDate.of(year, month, 1),
                type = type,
                category = category,
                amountFormatted = "",
                amount = 0,
                memo = ""
            ),
            isTypeLock = true,
            onCancelClick = { viewModel.onDismissAddTransactionDialog() }
        ) { viewModel.onConfirmAddTransactionDialog(it) }
    }

    if (uiState.showReplaceTransactionDialog && uiState.uiModel.selectedTransaction != null) {
        AllowanceInputDialog(
            chartUIModel = uiState.uiModel.selectedTransaction!!,
            isTypeLock = true,
            onCancelClick = { viewModel.onDismissReplaceDialog() }
        ) { viewModel.onConfirmReplaceDialog(it) }
    }

    if (uiState.showDeleteTransactionDialog && uiState.uiModel.selectedTransaction != null) {
        PopupDialog(
            title = "${uiState.uiModel.selectedTransaction!!.id}",
            description = stringResource(R.string.selected_transaction_delete),
            popupType = PopupType.TRANSACTION_DELETE,
            onCancelClick = { viewModel.onDismissDeleteDialog() },
            onConfirmClick = { viewModel.onConfirmDeleteDialog() }
        )
    }

    LaunchedEffect(Unit) {
        viewModel.applyInitialArgs(year, month, category, type)
        viewModel.load()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.flowWithLifecycle(lifecycleOwner.lifecycle).collectLatest { event ->
            when (event) {
                is UIEvent.SuccessShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            when (it) {
                is PayKidsException.DialogException -> {

                }

                is PayKidsException.SnackBarException -> {

                }

                is PayKidsException.ToastException -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }

            viewModel.clearError()
        }
    }

    when {
        uiState.isLoading -> {
            ScreenLoading()
        }

        else -> {
            CategoryDetailScreen(
                month = month,
                category = uiState.uiModel.category,
                totalAmount = uiState.uiModel.totalAmount,
                type = uiState.uiModel.allowanceType,
                details = uiState.uiModel.detailCategories,
                onAddDialog = { viewModel.onAddClickDialog() },
                onReplaceDialog = { viewModel.onReplaceTransactionDialog(it) },
                onDeleteDialog = { viewModel.onDeleteTransactionDialog(it) }
            )
        }
    }
}

@Composable
fun CategoryDetailScreen(
    month: Int,
    category: String,
    totalAmount: Int,
    type: AllowanceType,
    details: List<AllowanceChartUIModel>,
    onAddDialog: () -> Unit,
    onReplaceDialog: (AllowanceChartUIModel) -> Unit,
    onDeleteDialog: (AllowanceChartUIModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray5)
            .statusBarsPadding()
            .padding(
                start = CategoryDetailScreenStartEndPadding,
                end = CategoryDetailScreenStartEndPadding,
                top = CategoryDetailScreenTopPadding
            )
    ) {
        Text(
            text = "${month}월", style = AllowanceDiaryHeadMonthTextStyle
        )

        Spacer(Modifier.height(CategoryDetailScreenSpacer53))

        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(color = Blue1)) {
                    append(category)
                }
                withStyle(SpanStyle(color = Black)) {
                    append(
                        if (type == AllowanceType.EXPENSE) stringResource(
                            R.string.text_category_detail_expense,
                            formatAmount(totalAmount)
                        )
                        else stringResource(
                            R.string.text_category_detail_income,
                            formatAmount(totalAmount)
                        )
                    )
                }
            },
            style = CategoryDetailTitleTextStyle
        )

        Spacer(Modifier.height(CategoryDetailScreenSpacer24))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(details, key = { it.id }) {
                DetailItem(it, type, onReplaceDialog, onDeleteDialog)

                Spacer(Modifier.height(CategoryDetailScreenSpacer8))
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(Shape10)
                        .clickable(
                            onClick = {
                                onAddDialog()
                            }
                        )
                        .background(color = White2)
                        .shadow(
                            elevation = ShadowElevation16,
                            shape = Shape10,
                            ambientColor = MyPageCardShadowColor,
                            spotColor = MyPageCardShadowColor
                        )
                        .padding(
                            top = CategoryDetailScreenItemTopBottomPadding,
                            bottom = CategoryDetailScreenItemTopBottomPadding
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.text_add_category),
                        style = CategoryDetailItemAddTextStyle.copy(color = Gray7)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailItem(
    data: AllowanceChartUIModel,
    type: AllowanceType,
    onReplaceDialog: (AllowanceChartUIModel) -> Unit,
    onDeleteDialog: (AllowanceChartUIModel) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shape10)
            .combinedClickable(
                onClick = {
                    onReplaceDialog(data)
                },
                onLongClick = {
                    onDeleteDialog(data)
                }
            )
            .background(color = White)
            .shadow(
                elevation = ShadowElevation16,
                shape = Shape10,
                ambientColor = MyPageCardShadowColor,
                spotColor = MyPageCardShadowColor
            )
            .padding(
                start = CategoryDetailScreenItemStartEndPadding,
                end = CategoryDetailScreenItemStartEndPadding,
                top = CategoryDetailScreenItemTopBottomPadding,
                bottom = CategoryDetailScreenItemTopBottomPadding
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    data.category,
                    style = CategoryDetailItemCategoryTextStyle.copy(color = Blue1)
                )
                Spacer(Modifier.height(CategoryDetailScreenSpacer6))

                Text(
                    if (type == AllowanceType.EXPENSE) "-${formatAmount(data.amount)}"
                    else "+${formatAmount(data.amount)}",
                    style = CategoryDetailItemAmountTextStyle.copy(color = Black)
                )
            }

            Text(
                data.memo,
                style = CategoryDetailItemMemoTextStyle.copy(color = Gray1)
            )
        }
    }
}

@Preview
@Composable
fun CategoryDetailScreenPreview() {
}