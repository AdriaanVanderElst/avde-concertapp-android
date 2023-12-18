package com.example.androidconcertapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

enum class Route {
    ConcertList,
    ConcertDetail,
    ConcertCreate,
}

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Route.ConcertList.name,
    ) {
        composable(route = Route.ConcertList.name) {
            ConcertListScreen()
        }
    }
}

@Composable
fun ConcertListScreen() {
    Text(text = "test")
}
