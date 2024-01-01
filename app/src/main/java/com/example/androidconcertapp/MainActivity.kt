package com.example.androidconcertapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.example.androidconcertapp.ui.AppSwitcher
import com.example.androidconcertapp.ui.loginScreen.LocalUserState
import com.example.androidconcertapp.ui.loginScreen.UserStateViewModel
import com.example.androidconcertapp.ui.theme.ConcertAppTheme

/**
 * The main activity of the application.
 */
class MainActivity : ComponentActivity() {
    private val userState by viewModels<UserStateViewModel>(factoryProducer = { UserStateViewModel.Factory })

    /**
     * Sets the content of the activity.
     *
     * @param savedInstanceState the saved instance state.
     */
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConcertAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    CompositionLocalProvider(LocalUserState provides userState) {
                        AppSwitcher(windowSize)
                    }
                }
            }
        }
    }
}
