package com.paykidscompose.presentation.screens.study.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.components.OutlineInputField
import com.paykidscompose.presentation.ui.theme.StudyChatInputBoxHorizontalPadding
import com.paykidscompose.presentation.ui.theme.StudyChatInputBoxVerticalPadding
import com.paykidscompose.presentation.ui.theme.StudyChatInputFieldHeight
import com.paykidscompose.presentation.ui.theme.StudyChatInputFieldRound
import com.paykidscompose.presentation.ui.theme.StudyChatInputFieldStartPadding
import com.paykidscompose.presentation.ui.theme.StudyChatInputRoundTopEnd
import com.paykidscompose.presentation.ui.theme.StudyChatInputRoundTopStart
import com.paykidscompose.presentation.ui.theme.StudyChatInputShadowElevation
import com.paykidscompose.presentation.ui.theme.StudyChatInputTextStyle
import com.paykidscompose.presentation.ui.theme.StudyChatSendIconSize
import com.paykidscompose.presentation.ui.theme.White
import com.paykidscompose.presentation.ui.theme.White4

@Composable
fun ChatInput(
    text: String, onTextChanged: (String) -> Unit, onSendClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = StudyChatInputShadowElevation,
                shape = RoundedCornerShape(
                    StudyChatInputRoundTopStart, StudyChatInputRoundTopEnd
                )
            ),
        shape = RoundedCornerShape(
            StudyChatInputRoundTopStart, StudyChatInputRoundTopEnd
        ),
        colors = CardDefaults.cardColors(containerColor = White4)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = StudyChatInputBoxHorizontalPadding,
                    vertical = StudyChatInputBoxVerticalPadding
                ), contentAlignment = Alignment.Center
        ) {
            OutlineInputField(
                text = text,
                style = StudyChatInputTextStyle,
                onTextChange = onTextChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(StudyChatInputFieldHeight),
                hint = stringResource(R.string.hint_chat),
                startPadding = StudyChatInputFieldStartPadding,
                shape = RoundedCornerShape(StudyChatInputFieldRound),
                backgroundColor = White
            )
            IconButton(
                onClick = onSendClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = null,
                    modifier = Modifier.size(StudyChatSendIconSize)
                )
            }
        }
    }
}