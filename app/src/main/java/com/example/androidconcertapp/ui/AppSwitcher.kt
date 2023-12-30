package com.example.androidconcertapp.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.androidconcertapp.ui.loginScreen.LoginScreen
import com.example.androidconcertapp.ui.loginScreen.UserState
import com.example.androidconcertapp.ui.navigation.NavigationType



@Composable
fun AppSwitcher(windowSize: WindowSizeClass) {
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            BuildScreen(NavigationType.BOTTOM_NAVIGATION)
        }
        WindowWidthSizeClass.Medium -> {
            BuildScreen(NavigationType.NAVIGATION_RAIL)
        }
        WindowWidthSizeClass.Expanded -> {
            BuildScreen(NavigationType.PERMANENT_NAVIGATION_DRAWER)
        }
        else -> {
            BuildScreen(NavigationType.BOTTOM_NAVIGATION)
        }
    }
}
@Composable
fun BuildScreen(navigationType: NavigationType) {
    val vm = UserState.current
    if (vm.isLoggedIn) {
        ConcertApp(navigationType = navigationType)
    } else {
        LoginScreen(navigationType = navigationType)
    }
}

