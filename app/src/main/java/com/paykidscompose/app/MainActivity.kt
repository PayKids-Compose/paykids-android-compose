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
import com.paykidscompose.presentation.screens.login.nickname.NicknameScreen
import com.paykidscompose.presentation.screens.splash.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PayKidsComposeTheme {
                    Surface {
                        var showSplashScreen by remember { mutableStateOf(true) }
                        if (showSplashScreen) SplashScreen { showSplashScreen = false }
                        else NicknameScreen{}
                    }
            }
        }
    }
}