package com.example.androidconcertapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.androidconcertapp.MainNavHost

@Composable
fun ConcertApp() {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        topBar = { ConcertAppTopBar() },
        bottomBar = { ConcertAppBottomBar() },
    ) { innerPadding ->
        MainNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
    }
}
