package com.example.androidconcertapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidconcertapp.ui.listScreen.ConcertDetails
import com.example.androidconcertapp.ui.listScreen.ConcertList
import com.example.androidconcertapp.ui.listScreen.ConcertListViewModel

@Composable
fun NavComponent(
    navController: NavHostController,
    sharedViewModel: ConcertListViewModel,
    modifier: Modifier,
    goToDetail: (id: Int) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = ConcertScreen.List.name,
        modifier = modifier,
    ) {

        composable(route = ConcertScreen.List.name) {
            ConcertList(viewModel = sharedViewModel, goToDetail = goToDetail)
        }
        composable(route = "${ConcertScreen.Detail.name}/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            ConcertDetails(viewModel = sharedViewModel)
        }
    }
}