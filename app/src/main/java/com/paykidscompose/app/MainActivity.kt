package com.paykidscompose.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.paykidscompose.app.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.screens.login.LoginScreen
import com.paykidscompose.presentation.screens.login.nickname.NicknameScreen
import com.paykidscompose.presentation.screens.mypage.MyPageScreen
import com.paykidscompose.presentation.screens.mypage.info.MyInfoScreen
import com.paykidscompose.presentation.screens.splash.SplashScreen

enum class Screen{
    SPLASH, LOGIN, NICKNAME, MYPAGE, MYINFO
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PayKidsComposeTheme {
                Surface {
                    var currentScreen by remember { mutableStateOf(Screen.SPLASH)}

                    when (currentScreen) {
                        Screen.SPLASH -> SplashScreen { currentScreen = Screen.LOGIN }
                        Screen.LOGIN -> LoginScreen { currentScreen = Screen.NICKNAME }
                        Screen.NICKNAME -> NicknameScreen { currentScreen = Screen.MYPAGE }
                        Screen.MYPAGE -> MyPageScreen(onClickMyInfo = { currentScreen = Screen.MYINFO})
                        Screen.MYINFO -> MyInfoScreen()
                    }
                }
            }
        }
    }
}