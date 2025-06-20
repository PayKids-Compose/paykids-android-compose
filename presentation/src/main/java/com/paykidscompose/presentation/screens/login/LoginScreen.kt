package com.paykidscompose.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.paykidscompose.presentation.R
import com.paykidscompose.presentation.dummy.DummyUser
import com.paykidscompose.presentation.dummy.User
import com.paykidscompose.presentation.ui.components.InfoText
import com.paykidscompose.presentation.ui.theme.BottomButtonPadding
import com.paykidscompose.presentation.ui.theme.InfoTextStyle
import com.paykidscompose.presentation.ui.theme.LoginKakaoTextBottomPadding
import com.paykidscompose.presentation.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.ui.theme.StartAndEndPadding

@Composable
fun LoginScreen(onKakaoClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(R.drawable.bg_login),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = StartAndEndPadding,
                    end = StartAndEndPadding,
                    bottom = BottomButtonPadding
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InfoText(
                    text = stringResource(R.string.text_kakao_login),
                    style = InfoTextStyle
                )

                Spacer(modifier = Modifier.height(LoginKakaoTextBottomPadding))

                Image(
                    painter = painterResource(R.drawable.img_kakao_login),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            onKakaoClick()
                        }),
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

//
//@Composable
//fun KakaoButton(nickname: String, onKakaoClick: (User) -> Unit){
//    Box(
//        modifier = Modifier.background(Yellow).fillMaxWidth().clickable(onClick = {
//            val user = DummyUser.createDummyUser(nickname)
//            onKakaoClick(user)
//        }).border()
//
//    ){
//        Image(
//            painter = painterResource(R.drawable.ic_logo_provider_kakao),
//            contentDescription = null,
//            modifier = Modifier.align(Alignment.CenterStart)
//        )
//        Text(
//            text = stringResource(R.string.text_kakao_login_btn),
//            modifier = Modifier.align(Alignment.Center),
//            style = typography.bodyMedium
//        )
//
//    }
//}


@Preview
@Composable
fun LoginScreenPreview() {
    PayKidsComposeTheme {
        LoginScreen()
    }
}