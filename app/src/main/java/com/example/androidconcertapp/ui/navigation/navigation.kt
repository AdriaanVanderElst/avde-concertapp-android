package com.example.androidconcertapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidconcertapp.model.Concert
import com.example.androidconcertapp.ui.listScreen.ConcertDetails
import com.example.androidconcertapp.ui.listScreen.ConcertList

@Composable
fun NavComponent(navController: NavHostController, modifier: Modifier, goToDetail: (c: Concert) -> Unit) {
    NavHost(
        navController = navController,
        startDestination = ConcertScreen.List.name,
        modifier = modifier,
    ) {
        composable(route = ConcertScreen.List.name) {
            ConcertList(goToDetail = goToDetail)
        }
        composable(route = "${ConcertScreen.Detail.name}/{id}") {
            val id = it.arguments?.getString("id")?.toInt() ?: 0
            ConcertDetails(id)
        }
    }
}
