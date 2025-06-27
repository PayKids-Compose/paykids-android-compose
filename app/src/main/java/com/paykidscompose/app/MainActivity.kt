package com.paykidscompose.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.paykidscompose.app.ui.theme.PayKidsComposeTheme
import com.paykidscompose.presentation.navigation.PayKidsApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PayKidsComposeTheme {
                PayKidsApp()
            }
        }
    }
}