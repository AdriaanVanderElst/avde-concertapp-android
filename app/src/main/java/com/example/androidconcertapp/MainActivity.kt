package com.example.androidconcertapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.example.androidconcertapp.ui.AppSwitcher
import com.example.androidconcertapp.ui.loginScreen.LoginViewModel
import com.example.androidconcertapp.ui.loginScreen.UserState
import com.example.androidconcertapp.ui.theme.ConcertAppTheme

class MainActivity : ComponentActivity() {
    private val userState by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConcertAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    CompositionLocalProvider(UserState provides userState) {
                        AppSwitcher()
                    }
                }
            }
        }
    }
}
