package com.paykidscompose.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.ui.theme.Black
import com.paykidscompose.presentation.ui.theme.Blue2
import com.paykidscompose.presentation.ui.theme.Gray2
import com.paykidscompose.presentation.ui.theme.TitleColor
import com.paykidscompose.presentation.ui.theme.typography

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = TitleColor,
    style: TextStyle = typography.titleLarge
) {
    Text(
        text,
        modifier = modifier.fillMaxWidth(),
        style = style,
        color = color
    )
}

@Composable
fun InputField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    hintColor: Color = Gray2,
    fieldLine: InputFieldStyle = InputFieldStyle.Underline,
    containerColor: Color = Color.Transparent,
    focusedColor: Color = Blue2,
    unfocusedColor: Color = Gray2,
    textColor: Color = Black,
    textStyle: TextStyle = typography.titleMedium
) {
    when (fieldLine) {
        InputFieldStyle.Underline -> TextField(
            value = text,
            onValueChange = onValueChange,
            modifier = modifier.fillMaxWidth(),
            placeholder = { Text(hint, style = textStyle.copy(color = hintColor)) },
            textStyle = textStyle,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                disabledContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                unfocusedIndicatorColor = unfocusedColor,
                focusedIndicatorColor = focusedColor,
                focusedContainerColor = containerColor,
                focusedTextColor = textColor,
                unfocusedTextColor = textColor
            )
        )

        InputFieldStyle.Outline -> BasicTextField(
            value = text,
            onValueChange = onValueChange
            )
    }
}

@Preview(showBackground = true)
@Composable
fun TextPreview() {
}