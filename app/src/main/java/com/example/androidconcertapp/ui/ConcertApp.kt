package com.example.androidconcertapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConcertApp() {
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = navController.previousBackStackEntry != null
    val navigateUp: () -> Unit = { navController.navigateUp() }
    val goHome: () -> Unit = {
        navController.popBackStack(
            ConcertScreen.List.name,
            inclusive = false,
        )
    }
    val goToDetail: (id: Int) -> Unit = { id ->
        navController.navigate("${ConcertScreen.Detail.name}/$id")
    }
//    val currentScreenTitle = ConcertScreen.valueOf(
//        backStackEntry?.destination?.route ?: ConcertScreen.List.name,
//    ).title
    val currentScreenTitle = ConcertScreen.List.title

    Scaffold(
        topBar = {
            ConcertAppTopBar(
                canNavigateBack = canNavigateBack,
                navigateUp = navigateUp,
                currentScreenTitle = currentScreenTitle,
            )
        },
        bottomBar = { ConcertAppBottomBar(goHome) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ConcertScreen.List.name,
            modifier = Modifier.padding(paddingValues = innerPadding),
        ) {
            composable(route = ConcertScreen.List.name) {
                ConcertList(goToDetail = goToDetail)
            }
            composable(route = "${ConcertScreen.Detail.name}/{id}") {
                val id = it.arguments?.getString("id")?.toInt() ?: 0
                ConcertDetails(id = id)
            }
        }
    }
}
