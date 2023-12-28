package com.example.androidconcertapp.ui

import androidx.compose.runtime.Composable
import com.example.androidconcertapp.ui.loginScreen.LoginScreen
import com.example.androidconcertapp.ui.loginScreen.UserState

@Composable
fun AppSwitcher() {
    val vm = UserState.current
    if (vm.isLoggedIn) {
        ConcertApp()
    } else {
        LoginScreen()
    }
}
