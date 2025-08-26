package com.paykidscompose.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.CardShadowElevation
import com.paykidscompose.presentation.ui.theme.FieldAndInfoSpacer
import com.paykidscompose.presentation.ui.theme.Gray2
import com.paykidscompose.presentation.ui.theme.Gray6
import com.paykidscompose.presentation.ui.theme.Gray7
import com.paykidscompose.presentation.ui.theme.InfoTextStyle
import com.paykidscompose.presentation.ui.theme.MyInfoCardNicknameTextStyle
import com.paykidscompose.presentation.ui.theme.NicknameFieldTextStyle
import com.paykidscompose.presentation.ui.theme.NicknameScreenFieldBoxHeight
import com.paykidscompose.presentation.ui.theme.NicknameTitleTextStyle
import com.paykidscompose.presentation.ui.theme.OutlineBorder
import com.paykidscompose.presentation.ui.theme.OutlineDefaultShadowElevation
import com.paykidscompose.presentation.ui.theme.OutlineDefaultTextStartPadding
import com.paykidscompose.presentation.ui.theme.OutlineShape
import com.paykidscompose.presentation.ui.theme.QuizShortAnswerTextStyle
import com.paykidscompose.presentation.ui.theme.ShortAnswerQuizOutlineHeight
import com.paykidscompose.presentation.ui.theme.ShortAnswerQuizOutlineShape
import com.paykidscompose.presentation.ui.theme.TitleColor
import com.paykidscompose.presentation.ui.theme.White

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = TitleColor,
    style: TextStyle = NicknameTitleTextStyle,
    textAlign: TextAlign? = null
) {
    Text(
        text,
        modifier = modifier,
        style = style,
        color = color,
        textAlign = textAlign
    )
}

@Composable
fun InfoText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = White,
    style: TextStyle = InfoTextStyle
) {
    Text(
        text = text,
        color = textColor,
        style = style,
        modifier = modifier
    )
}

@Composable
fun UnderlineInputField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    hintColor: Color = Gray2,
    style: TextStyle = NicknameFieldTextStyle
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier,
        textStyle = style,
        singleLine = true,
        decorationBox = { innerTextField ->
            Column(
                modifier = modifier
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (text.isEmpty()) {
                        Text(
                            text = hint,
                            style = NicknameFieldTextStyle.copy(color = hintColor),
                        )
                    }

                    innerTextField()
                }

                Spacer(modifier = Modifier.height(FieldAndInfoSpacer))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Gray2)
                        .height(NicknameScreenFieldBoxHeight)
                )
            }
        }
    )
}


@Composable
fun OutlineInputField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    startPadding: Dp = OutlineDefaultTextStartPadding,
    readOnly: Boolean = false,
    outlineColor: Color = Gray6,
    backgroundColor: Color = Color.Unspecified,
    hint: String = "",
    hintColor: Color = Gray7,
    textColor: Color = Black,
    style: TextStyle = MyInfoCardNicknameTextStyle.copy(color = Black),
    textAlign: TextAlign = TextAlign.Start,
    contentAlignment: Alignment = Alignment.CenterStart,
    shape: Shape = RoundedCornerShape(OutlineShape),
    singleLine: Boolean = true,
    enabled: Boolean = true,
    shadowElevation: Dp = OutlineDefaultShadowElevation,
    shadowColor: Color = Color.Unspecified
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = shadowElevation,
                shape = shape,
                ambientColor = shadowColor,
                spotColor = shadowColor
            )
            .clip(shape)
            .background(color = backgroundColor)
            .border(OutlineBorder, outlineColor, shape)
            .padding(start = startPadding)
            .then(modifier),
        textStyle = style.copy(
            color = textColor,
            textAlign = textAlign
        ),
        singleLine = singleLine,
        readOnly = readOnly,
        enabled = enabled,
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = contentAlignment,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (text.isEmpty()) {
                    Text(text = hint, style = style.copy(color = hintColor))
                }
                innerTextField()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TextPreview() {
    OutlineInputField(
        text = "user Input",
        onTextChange = {

        },
        startPadding = 0.dp,
        modifier = Modifier
            .height(ShortAnswerQuizOutlineHeight),
        backgroundColor = White,
        outlineColor = Gray2,
        hint = stringResource(R.string.hint_input_answer),
        hintColor = Gray2,
        style = QuizShortAnswerTextStyle,
        shape = RoundedCornerShape(ShortAnswerQuizOutlineShape),
        shadowElevation = CardShadowElevation,
        shadowColor = Gray2
    )
}